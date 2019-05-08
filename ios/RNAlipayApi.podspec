
Pod::Spec.new do |s|
  s.name         = "RNAlipayApi"
  s.version      = "1.0.3"
  s.summary      = "RNAlipayApi"
  s.description  = <<-DESC
                  RNAlipayApi
                   DESC
  s.homepage     = "https://github.com/liuhong1happy/react-native-alipay"
  s.license      = "MIT"
  s.author             = { "liuhong1happy" => "liuhong1.happy@163.com" }
  s.platform     = :ios, "7.0"
  s.source       = { :path => "https://github.com/liuhong1happy/react-native-alipay.git", :tag => "#{s.version}" }
  s.source_files  = "react-native-alipay/ios/*.{h,m}"
  s.requires_arc = true

  s.dependency "React"
  s.dependency "AlipaySDK-iOS"
  #s.dependency "others"
end

  