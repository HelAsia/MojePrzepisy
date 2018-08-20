import re
import string

def checkIsInteger(x):
    try:
        int(x)
        return True
    except TypeError:
        return False