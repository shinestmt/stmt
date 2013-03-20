# encoding=UTF-8

shoplist = ['apple', 'mango', 'carrot', 'banana']

# Indexing or 'Subscription' operation 
print 'Item 0 is', shoplist[0]                      # 从列表选择单个项
print 'Item 1 is', shoplist[1]                      # 
print 'Item 2 is', shoplist[2]                      # 
print 'Item 3 is', shoplist[3]                      # 
print 'Item -1 is', shoplist[-1]                    # 索引倒数第一个 banana
print 'Item -2 is', shoplist[-2]                    # 索引倒数第二个 carrot

# Slicing on a list                                 # 从列表选择区域
print 'Item 1 to 3 is', shoplist[1:3]               # 索引1到3(不含结尾)
print 'Item 2 to end is', shoplist[2:]              # 索引2到最后
print 'Item 1 to -1 is', shoplist[1:-1]             # 索引1到倒数第一(从前往后)
print 'Item start to end is', shoplist[:]           # 从起始到结尾

# Slicing on a string                               # 从字符串选择
name = 'swaroop'                                    # 
print 'characters 1 to 3 is', name[1:3]             # 
print 'characters 2 to end is', name[2:]            # 
print 'characters 1 to -1 is', name[1:-1]           # 
print 'characters start to end is', name[:]         # 


print __name__