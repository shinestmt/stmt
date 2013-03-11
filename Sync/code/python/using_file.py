# encoding=UTF-8
"""
文件操作(不支持中文)

"""

print 'File : D:/123.txt'
print '--------------------------------------------'
f = file('D:/123.txt', 'r')   # 以只读方式打开: 'r'ead

while True:
    line = f.readline()       # 读取一行
    if len(line) == 0:        # 0表示EOF(End Of File)
        break
    print line,               # 逗号避免自动换行
f.close()                     # 关闭连接