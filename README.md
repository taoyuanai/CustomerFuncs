# CustomerFuncs
自定义hive函数：
自定义hive函数并打包并将引用的私钥文件存入emr集群：
https://github.com/taoyuanai/CustomerFuncs
header-1：hadoop:hadoop /etc/ecm/common-conf/se-cost-dw/rsa.key
worker-1：hadoop:hadoop /etc/ecm/common-conf/se-cost-dw/rsa.key
worker-2：hadoop:hadoop /etc/ecm/common-conf/se-cost-dw/rsa.key

Jar包放至HDFS后加载函数并测试之：
[root@emr-header-1 ~]# su hadoop
hive> use se_cost_platform_stage;
hive> create function decrypto as 'app.hive.func.DeCrypto' using jar 'hdfs:///user/hive/customerfunc/CustomerFuncs-1.0-SNAPSHOT.jar';
hive> select se_cost_platform_stage.decrypto("");
hive> select se_cost_platform_stage.decrypto("J5Pv8UlufkREQvh56usJBgoytogQZWpdXZPDBNpmVT5X0cORv3WcVMwkkW9D8DzEOBeZ145JXnVHpB0hue0/IOW+BdHxS7XimpYvf3/+Y23LEvIxn79wxorufVbL5iFBzBkPMMMDeaqiIlQpSQQ5OdEj/0WXXkQJyP3rZEzz/tDRXFNXRCnQGeG785Kcmc0eZEjOiO+adis5yy/VGqO6uaPm/Q5iXp5kSKONt0td4xgcuGQBX9u7qzjmsHsjlKuI3kgj5v2i9bXICQBraz1t7LUrXZ4jwda1i6N5JHNJLQQhv+tMdCVj1Xjdm/yTa7BammsVteqHM6bkwKWg2CI2FQ==")/2;
————————————————————
