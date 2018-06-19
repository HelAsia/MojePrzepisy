import sys


class Logger:
    @staticmethod
    def _out(x):
        sys.stderr.write(x + '\n')

    @staticmethod
    def dbg(x):
        sys.stderr.write('[dbg] ' + x + '\n')

    @staticmethod
    def out(x):
        Logger._out('[.] ' + x)

    @staticmethod
    def info(x):
        Logger._out('[?] ' + x)

    @staticmethod
    def err(x):
        sys.stderr.write('[!] ' + x + '\n')

    @staticmethod
    def fail(x):
        Logger._out('[-] ' + x)

    @staticmethod
    def ok(x):
        Logger._out('[+] ' + x)