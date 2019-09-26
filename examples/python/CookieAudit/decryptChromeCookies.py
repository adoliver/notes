#! /usr/bin/env python3
'''
The MIT License (MIT)

Copyright (c) 2015 Nathan Henrie

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

Adopted from blog post http://n8h.me/HufI1w 
Original author maintains a version of the original at https://github.com/n8henrie/pycookiecheat/blob/master/src/pycookiecheat/pycookiecheat.py

If interfaces with keychain etc begin to fail I would suggest referencing the maintained version for updates.

Modified the original code to run queries against the Chrome sqlite cookie table instead of navigating to a url. Made use of the decryption features to support a manual security audit of cookies set on myFICO website pages.
Queries do the following:
    * create a new column decrypted_value on the cookie table.
    * query all entries in the cookie table
    * generate update statements to update decrypted_value with the plain text data from the existing encrypted_value column.
    * set all decrypted_value entries to empty string when the --clear flag is used.
This script is intended to be used in the following way for investigation:
    1. clear chrome cookies from inside the browser
    2. navigate to pages being investigated
    3. run this script to get decrypted values
    4. query the cookie table to retrieve information about the cookies being set
    WARNING: this method uses the currently live data from chrome's cookie table. If too much time is spent navigating in step #2 you will not see short-expiration cookies in step #4. For example if the cookie has a 30sec expiration chrome could have removed it by the time your query in step #4 is run.
'''

import argparse
import sqlite3
import os.path
import urllib.parse
import keyring
import sys
from Crypto.Cipher import AES
from Crypto.Protocol.KDF import PBKDF2

def chrome_cookies(userProfile, isClear):
    salt = b'saltysalt'
    iv = b' ' * 16
    length = 16
    

    print("chrome_cookies")

    def chrome_decrypt(encrypted_value, key=None):
        print("chrome_decrypt")

        # Encrypted cookies should be prefixed with 'v10' according to the
        # Chromium code. Strip it off.
        encrypted_value = encrypted_value[3:]

        # Strip padding by taking off number indicated by padding
        # eg if last is '\x0e' then ord('\x0e') == 14, so take off 14.
        # You'll need to change this function to use ord() for python2.
        def clean(x):
            return x[:-x[-1]].decode('utf8')

        cipher = AES.new(key, AES.MODE_CBC, IV=iv)
        decrypted = cipher.decrypt(encrypted_value)

        return clean(decrypted)

    # If running Chrome on OSX
    if sys.platform == 'darwin':
        print("found OSX, get Chrome Safe Storage from keychain")
        my_pass = keyring.get_password('Chrome Safe Storage', 'Chrome')
        print("encode my_pass")
        my_pass = my_pass.encode('utf8')
        iterations = 1003
        print("open chome cookie database for profile".format(userProfile))
        cookie_file = os.path.expanduser(
            '~/Library/Application Support/Google/Chrome/{}/Cookies'.format(userProfile)
        )

    # If running Chromium on Linux
    elif sys.platform == 'linux':
        my_pass = 'peanuts'.encode('utf8')
        iterations = 1
        cookie_file = os.path.expanduser(
            '~/.config/chromium/{}/Cookies'.format(userProfile)
        )
    else:
        raise Exception("This script only works on OSX or Linux.")

    # Generate key from values above
    key = PBKDF2(my_pass, salt, length, iterations)

    # Part of the domain name that will help the sqlite3 query pick it from the Chrome cookies
    #domain = urllib.parse.urlparse(url).netloc
    #domain_no_sub = '.'.join(domain.split('.')[-2:])

    conn = sqlite3.connect(cookie_file)
    sql_table_info = 'PRAGMA table_info("cookies")'
    sql_add_dycrypt = "ALTER TABLE cookies ADD decrypted_value TEXT NOT NULL DEFAULT ''"
    sql_clear = "update cookies set decrypted_value=\'\' where 1=1"
    sql_select = 'select name, "path", value, encrypted_value from cookies'

    with conn:
        # Check to see if decrypted_value column exists
        needsDecryptColumn = True
        for cid, name, data_type, not_null, dflt_value, pk in conn.execute(sql_table_info):
            if name == "decrypted_value":
                needsDecryptColumn = False

        if needsDecryptColumn:
            conn.execute(sql_add_dycrypt)
            

        if isClear:
            print("set all decrypted_value entries to empty string")
            conn.execute(sql_clear)
        else :
            print("decrypt cookie values into decrypted_value column")
            for name, path, value, encrypted_value in conn.execute(sql_select):

                # if there is a not encrypted value or if the encrypted value
                # doesn't start with the 'v10' prefix, return v
                decrypted_val = ""
                if value or (encrypted_value[:3] != b'v10'):
                    decrypted_val = value
                else:
                    decrypted_tuple = (name, chrome_decrypt(encrypted_value, key=key))
                    decrypted_val = decrypted_tuple[1]

                decrypted_val = decrypted_val.replace("'","''") # escape single quotes for use in building hte sql query.
                # Store the unencrypted value in new decrypted_value column
                sql_decrypt = 'update cookies set decrypted_value=\'{}\' '\
                    'where name=\'{}\' and "path"=\'{}\' '.format(
                        decrypted_val,
                        name,
                        path)
                print(sql_decrypt)
                conn.execute(sql_decrypt)

# Retrive command line parameters
parser = argparse.ArgumentParser(description="decrypt chrome cookies into new decrypted_value column.")
parser.add_argument("--clear", action="store_true", help="Clear decrypted values from custom decrypted_value column.")
parser.add_argument("--profile", type=str, default="Default", help="Profile directory name found under ~/Library/Application\ Support/Google/Chrome/ on OSX. (default=Default)")

args = parser.parse_args()

chrome_cookies(args.profile, args.clear)
