# encoding=UTF-8

"""
sys.argv测试
==============================================
Demo: 
Command: argv.py -fafas -afa -fas
Result : ['argv.py', '-fafas', '-afa', '-fas']
----------------------------------------------
argv[0]  当前文件路径
其他分别为参数

"""

import sys
print sys.argv
print sys.version_info