# -*- coding: utf-8 -*-


from flask import Flask, url_for, redirect, render_template, request, Response, stream_with_context,jsonify
from itsdangerous import TimedJSONWebSignatureSerializer as Serializer
import json
import os
import time
import codecs
from flask_cors import *
from pydub import AudioSegment
import lsqlite3
app = Flask(__name__)
CORS(app, resources=r'/*')

databaseUse = 'sqlite3'


# 其他接口
# wifi定位 根据RSSI数据 返回数据库中最接近的 坐标
@app.route("/zhaozhiwen",methods=['GET'])
def zhaozhiwen():
	key = request.args.get('key','erro')
	if key!='1@1231dwe323e5dK':
		return ''
		pass
	table = 'zhiwen'
	database = 'admin'
	if databaseUse=='sqlite3':
		arr = lsqlite3.useSqliteSelect2(database,table)
		r1 = request.args.get('r1','0.0')
		r2 = request.args.get('r2','0.0')
		r3 = request.args.get('r3','0.0')
		r4 = request.args.get('r4','0.0')
		r5 = request.args.get('r5','0.0')
		r6 = request.args.get('r6','0.0')
		t = {}
		at = 100
		for x in arr:
			jieguo = (float(x['r1'])-float(r1))+(float(x['r2'])-float(r2))+(float(x['r3'])-float(r3))+(float(x['r4'])-float(r4))+(float(x['r5'])-float(r5))+(float(x['r6'])-float(r6))
			if jieguo<100:
				at=jieguo
				t=x
				pass
			pass
		return json.dumps({"msg":"获取成功","data":t})


if __name__ == '__main__':
	app.run(host='0.0.0.0', port=8002,debug="True")