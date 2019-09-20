#! /usr/bin/env python3
'''pyCookieCheat.py
2015022 Now its own GitHub repo, and in PyPi. 
    - For most recent version: https://github.com/n8henrie/pycookiecheat
    - This gist unlikely to be maintained further for that reason.
20150221 v2.0.1: Now should find cookies for base domain and all subs.
20140518 v2.0: Now works with Chrome's new encrypted cookies.
See relevant post at http://n8h.me/HufI1w
Use your browser's cookies to make grabbing data from login-protected sites easier.
Intended for use with Python Requests http://python-requests.org
Accepts a URL from which it tries to extract a domain. If you want to force the domain,
just send it the domain you'd like to use instead.
Intended use with requests:
    import requests
    import pyCookieCheat
    url = 'http://www.example.com'
    s = requests.Session()
    cookies = pyCookieCheat.chrome_cookies(url)
    s.get(url, cookies = cookies)
Adapted from my code at http://n8h.me/HufI1w
Helpful Links:
* Chromium Mac os_crypt: http://n8h.me/QWRgK8
* Chromium Linux os_crypt: http://n8h.me/QWTglz
* Python Crypto: http://n8h.me/QWTqte
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

                # Store the unencrypted value in new decrypted_value column
                sql_decrypt = 'update cookies set decrypted_value=\'{}\' '\
                    'where name=\'{}\' and "path"=\'{}\' '.format(
                        decrypted_val.replace("'","\'"), #escape any single quotes
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
