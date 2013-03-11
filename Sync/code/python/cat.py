# encoding=UTF-8
"""
异常处理

"""

import sys

# 读取文件内容
def readfile(filename):
    '''Print a file to the standard output.'''
    f = file(filename)
    while True:
        line = f.readline()
        if len(line) == 0:
            break
        print line, # notice comma
    f.close()

# Script starts from here
if len(sys.argv) < 2:
    print 'No action specified.'
    sys.exit()

if sys.argv[1].startswith('--'):
    option = sys.argv[1][2:]                           # sys.argv[1][2:]表示第一个参数字符串, 的从2到结尾
    if option == 'version':                            # 版本信息
        print 'Version 1.2'
    elif option == 'help':                             # 帮助信息
        print '''\
This program prints files to the standard output.
Any number of files can be specified.
Options include:
  --version : Prints the version number
  --help    : Display this help'''
    else:
        print 'Unknown option.'
    sys.exit()
else:
    for filename in sys.argv[1:]:
        readfile(filename)