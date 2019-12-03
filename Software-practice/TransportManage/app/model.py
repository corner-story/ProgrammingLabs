""" 
    数据实体
"""
from app.extenstions import db
from werkzeug.security import generate_password_hash, check_password_hash
from datetime import datetime

# 司机
# 司机方：姓名、联系方式、车型、车牌号、可运输类型等
class Driver(db.Model):
    id = db.Column(db.Integer, primary_key=True, autoincrement = True)
    username = db.Column(db.String(120), nullable=False)
    password_hash = db.Column(db.String(600), nullable=False)
    phone_number = db.Column(db.String(32))
    email = db.Column(db.String(64))
    car_type = db.Column(db.String(64))
    car_number = db.Column(db.String(64))
    transport_type = db.Column(db.String(255))
    account = db.Column(db.String(255))
    user_type = db.Column(db.String(32), default="Driver")

    # 一个司机可以有多个申请
    applications = db.relationship("Application", backref="driver")

    @property
    def password(self):
        return self.password_hash
    @password.setter
    def password(self, password):
        self.password_hash = generate_password_hash(password)
    
    def check_password(self, password):
        return check_password_hash(self.password_hash, password)

    def __repr__(self):
        return f"<Driver: {username}, {phone_number}>"


# 货主方包括：姓名、联系方式、常用货物、住址等
class Consigner(db.Model):
    id = db.Column(db.Integer, primary_key=True, autoincrement = True)
    username = db.Column(db.String(120), nullable=False)
    password_hash = db.Column(db.String(600), nullable=False)
    phone_number = db.Column(db.String(32))
    email = db.Column(db.String(64))
    general_good = db.Column(db.String(255))
    address = db.Column(db.String(320))
    account = db.Column(db.String(255))
    user_type = db.Column(db.String(32), default="Consigner")

    # 发货人和货物一对多
    goods = db.relationship("Good", backref="consigner")

    @property
    def password(self):
        return self.password_hash
    
    @password.setter
    def password(self, password):
        self.password_hash = generate_password_hash(password)
    
    def check_password(self, password):
        return check_password_hash(self.password_hash, password)

    def __repr__(self):
        return f"<Consigner: {username}, {phone_number}>"


class Good(db.Model):
    id = db.Column(db.Integer, primary_key=True, autoincrement = True)
    good_name = db.Column(db.String(255), nullable = False, index=True)
    good_type = db.Column(db.String(255), index=True)
    transport_origin = db.Column(db.String(255), nullable = False, index=True)
    transport_des = db.Column(db.String(255), nullable= False, index=True)
    transport_money = db.Column(db.String(255))
    good_status = db.Column(db.String(120), default="等待司机承运")
    timestamp = db.Column(db.DateTime, default=datetime.utcnow, index=True)

    # 发货人和货物是一对多
    consigner_id = db.Column(db.Integer, db.ForeignKey("consigner.id"))

    # 一个货物对应多个申请
    applications = db.relationship("Application", backref="good")

    def __repr__(self):
        return f"<Good: {good_name}, {good_status}>"


class Application(db.Model):
    id = db.Column(db.Integer, primary_key=True, autoincrement = True)
    result = db.Column(db.String(120), default="等待审核")

    # 一个司机可以有多个申请
    driver_id = db.Column(db.Integer, db.ForeignKey("driver.id"))

    # 一个货物对应多个申请
    good_id = db.Column(db.Integer, db.ForeignKey("good.id"))

    def __repr__(self):
        return f"<Application: {id}, {result}>"