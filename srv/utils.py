import re
import string

def checkIsInteger(x):
    try:
        int(x)
        return True
    except TypeError:
        return False

def normalizeTime(x):
    x = x.replace(" AM", "")
    x = x.replace(" PM", "")
    return x