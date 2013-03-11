# coding=UTF-8                                               # 避免中文引起的错误

zoo = ('wolf', 'elephant', 'penguin')                        # 定义元组
print 'Number of animals in the zoo is', len(zoo)            # 显示元组内容
new_zoo = ('monkey', 'dolphin', zoo)                         # 根据原来的元组,创建新的元组
print 'Number of animals in the new zoo is', len(new_zoo)    # 
print 'All animals in new zoo are', new_zoo                  # 
print 'Animals brought from old zoo are', new_zoo[2]         # 
print 'Last animal brought from old zoo is', new_zoo[2][2]   # 
