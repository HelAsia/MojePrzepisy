import sys


class Logger:
    @staticmethod
    def _out(x):
        sys.stderr.write(unicode(x) + u'\n')

    @staticmethod
    def dbg(x):
        sys.stderr.write(u'[dbg] ' + unicode(x) + u'\n')

    @staticmethod
    def out(x):
        Logger._out(u'[.] ' + unicode(x))

    @staticmethod
    def info(x):
        Logger._out(u'[?] ' + unicode(x))

    @staticmethod
    def err(x):
        sys.stderr.write(u'[!] ' + unicode(x) + u'\n')

    @staticmethod
    def fail(x):
        Logger._out(u'[-] ' + unicode(x))

    @staticmethod
    def ok(x):
        Logger._out(u'[+] ' + unicode(x))