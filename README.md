# PrivacyPolicyComplianceCheck
隐私政策合规检查方案
![日志](https://img-blog.csdnimg.cn/fe116e9ab8b04e4c8dc48d92d36f3d6e.png)

详细说明查看：https://blog.csdn.net/u011106915/article/details/120372099

对于涉及用户隐私的API的行为，无法通过手工log、UI等方式有效的方案进行；
# 转换思路进行hook
Android系统隐私权限的API方法可以通过Xposed进行hook处理，监听到相关方法的调用；
目前代码中监控的API涉及：（也可以手动添加）
- ActivityManager
- ApplicationPackageManager
- InetAddress
- NetworkInterface
- WifiManager
- Settings
- SensorManager
- TelephonyManager
- LocationManager

# 实现步骤
准备一个Xposed的设备
运行脚本至设备上
在Xposed中启动插件，重启手机，再打开APP
通过过滤’hookLog日志进行查看

