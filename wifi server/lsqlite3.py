# -*- coding: utf-8 -*-
import sqlite3


def useSqliteSelect2(database,table):
	conn = sqlite3.connect(r'database/'+database+'.sqlite3')
	conn.row_factory = dict_factory 
	cursor = conn.cursor()
	# cursor.execute("SELECT * from "+table+" where YuYan = 'java'")
	print("SELECT * from "+table)
	cursor.execute("SELECT * from "+table)
	values = cursor.fetchall()
	cursor.close()
	conn.commit()
	conn.close()
	return values
