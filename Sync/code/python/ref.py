# encoding=UTF-8

shoplist = ['apple', 'mango', 'carrot', 'banana']

print '-----------------------------------------------------'
print 'Simple Assignment'                                # 简单赋值
mylist = shoplist
del shoplist[0]                                          # 删除shoplist内的数据, mylist内的数据同时被删除

print 'shoplist is', shoplist
print 'mylist   is', mylist


print '-----------------------------------------------------'
print 'Copy by making a full slice'                      # 复制副本
mylist = shoplist[:]
del mylist[0]                                            # 删除shoplist内的数据, mylist内的数据不变

print 'shoplist is', shoplist
print 'mylist   is', mylist