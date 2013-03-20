# encoding=UTF-8

"""
Oracle 操作测试
"""

import cx_Oracle

conn = cx_Oracle.connect('bi_dim/bi_dim@192.168.1.74/orcl')
cursor = conn.cursor()
cursor.execute('select table_name from user_tables')
one = cursor.fetchone()
print one
print '----------------------------------------'
many = cursor.fetchmany(5)
print many
print '----------------------------------------'
al = cursor.fetchall()
print al
print '----------------------------------------'

cursor.prepare('select table_name from user_tables where rownum <= :id')
cursor.execute(None, {'id':5})
al2 = cursor.fetchall()
print al2

cursor.close()
conn.close()