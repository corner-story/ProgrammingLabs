import os

# 设置secret key
SECRET_KEY = os.urandom(24)

# 数据库配置
# mysql+pymysql://username:password@localhost:port/mydatabase
SQLALCHEMY_DATABASE_URI = "mysql+pymysql://{}:{}@localhost:3306/" \
                          "transport".format(os.environ.get("USERNAME"), os.environ.get("PASSWORD"))
SQLALCHEMY_TRACK_MODIFICATIONS = False