# encoding=UTF-8
"""
异常处理

"""

import sys

try:
    s = raw_input('Enter something --> ')
except EOFError:
    print '\nWhy did you do an EOF on me?'
    sys.exit()                                     # 直接退出程序
except:
    print '\nSome error/exception occurred.'       # 程序往下执行
finally:
    print "This's finally "
print 'Done'