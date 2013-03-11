"""
持久地 储存对象

"""
# encoding=UTF-8

import cPickle as p                                     # 导入外部模块
#import pickle as p

shoplistfile = 'shoplist.data'                          # 数据将持久化到此文件

shoplist = ['apple', 'mango', 'carrot']

f = file(shoplistfile, 'w')                             # 创建一个可写入文件对象
p.dump(shoplist, f)                                     # 持久化到文件
f.close()

del shoplist                                            # 删除原数据

f = file(shoplistfile)
storedlist = p.load(f)                                  # 取出储存的数据, 还原成对象
print storedlist