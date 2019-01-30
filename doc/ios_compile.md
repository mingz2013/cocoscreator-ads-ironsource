- 生成工程
cocoscreator 构建发布-》选择ios选项，link模板，构建。生成./build/jsb-link目录

- 安装依赖
cd ./build/jsb-link/frameworks/runtime-src/proj.ios_mac/ && pod install


- 修改工程
根据pod install的提示：

用xcode打开.xcworkspace项目文件。
修改 build settings->search paths -> 



- 加入oc文件
xcode工程里，将新加的oc文件添加进项目。
./build/jsb-link/frameworks/runtime-src/proj.ios_mac/ios/*



- 编译，运行


