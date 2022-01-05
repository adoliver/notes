import unittest
from somemodule import func_to_test

class TestBasic(unittest.TestCase):
    def test_one(self):
        self.assertTrue(func_to_test())
    def test_two(self):
        self.assertFalse(func_to_test())
