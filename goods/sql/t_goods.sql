/*
Navicat MySQL Data Transfer

Source Server         : seckill_user
Source Server Version : 80030
Source Host           : localhost:3306
Source Database       : seckill

Target Server Type    : MYSQL
Target Server Version : 80030
File Encoding         : 65001

Date: 2023-03-16 12:59:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `name` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品名称',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标题',
  `img` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品图片',
  `details` longtext COLLATE utf8mb4_general_ci COMMENT '商品详情',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '商品价格',
  `stock` int DEFAULT '0' COMMENT '商品库存，-1代表没有限制',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of t_goods
-- ----------------------------
INSERT INTO `t_goods` VALUES ('1', '商品1', '日抛美瞳女30片装一次性混血风10片装LZ旗舰店正品官网隐形眼镜TN', 'O1CN01evZwdj2BkGFty9p9t_!!0-item_pic.jpg_300x300q90.jpg', '无', '33.90', '767');
INSERT INTO `t_goods` VALUES ('2', '商品2', '岩板茶几电视柜组合意式极简可伸缩地柜现代简约客厅大理石电视柜', 'O1CN01HEKmuA1CrPsJnghqX_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '1700.00', '173');
INSERT INTO `t_goods` VALUES ('3', '商品3', 'KATO遮瑕膏三色遮暇盘液官方正品旗舰店推荐奶酪笔遮盖脸部黑眼圈', 'O1CN01V5mTPD1pzRqqH708x_!!0-item_pic.jpg_300x300q90.jpg', '无', '20.80', '551');
INSERT INTO `t_goods` VALUES ('4', '商品4', 'OKWE 原创设计 美式复古高街重磅纯棉短袖t恤男小众街头潮牌上衣', 'O1CN01VT0hnz1D07PpPXk0U_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '109.00', '221');
INSERT INTO `t_goods` VALUES ('5', '商品5', '适用苹果13/14手机壳iPhone12ProMax气囊11防摔XR新款7/8Plus透明se3保护Xs套男X镜头高级感女款S硅胶7全包2', 'O1CN01sPDIO91EMOFYSyx7c_!!0-item_pic.jpg_300x300q90.jpg', '无', '5.74', '439');
INSERT INTO `t_goods` VALUES ('6', '商品6', '包包2023新款潮冬法式复古简约托特包小众大容量大包女百搭单肩包', 'O1CN01Jr5Rg32Mb3xzUa6F9_!!732039845.jpg_300x300q90.jpg', '无', '9.90', '517');
INSERT INTO `t_goods` VALUES ('7', '商品7', '【电子梦游】地雷系量产型原创水色系统连帽外套宽松oversize', 'O1CN015tayTh1Clv79zHypp_!!86940122.jpg_300x300q90.jpg', '无', '218.00', '254');
INSERT INTO `t_goods` VALUES ('8', '商品8', '韩国雪纺衫女夏季薄款大码短袖上衣白色职业衬衫法式复古气质衬衣', 'O1CN01bABGMi2MNmWaweirj_!!3345649816.jpg_300x300q90.jpg', '无', '296.00', '703');
INSERT INTO `t_goods` VALUES ('9', '商品9', 'zuom 可爱感叹号小狗适用ip14苹果13promax手机壳iphone12mini防摔11女新款s创意动画xr透明8plus硅胶软壳7p', 'O1CN01d7vZBV1DsA6f76DBH_!!0-item_pic.jpg_300x300q90.jpg', '无', '6.90', '740');
INSERT INTO `t_goods` VALUES ('10', '商品10', '茱莉安法式奢华艺术隔断雕花手绘风景贴金银箔客厅复古双面屏风', 'O1CN01RdjUAn2Hk9gAyzRvW_!!53379188.jpg_300x300q90.jpg', '无', '25794.00', '574');
INSERT INTO `t_goods` VALUES ('11', '商品11', '买1送1美瞳半年抛黑色大直径隐形眼镜年抛女正品官网2021新款月抛', 'O1CN014lkAFp1gSAVXmpcme_!!0-item_pic.jpg_300x300q90.jpg', '无', '59.90', '636');
INSERT INTO `t_goods` VALUES ('12', '商品12', '悠哈味觉糖巨峰葡萄味酷露露爆浆果汁糖QQ糖橡皮糖水果味夹心软糖', 'O1CN01CY2Qdm1IVmr042hsd_!!0-item_pic.jpg_300x300q90.jpg', '无', '13.90', '446');
INSERT INTO `t_goods` VALUES ('13', '商品13', '搞怪沙雕生日礼物女生送给闺蜜的实用女朋友男生创意仪式感圣诞节', 'O1CN01VPWif61GRXxY2ACvn_!!0-item_pic.jpg_300x300q90.jpg', '无', '36.00', '306');
INSERT INTO `t_goods` VALUES ('14', '商品14', '高端男士休闲裤春秋2023潮牌直筒修身弹力小脚裤印花百搭裤子男款', 'O1CN01sx8KNw20KBwfevgXh_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '168.00', '178');
INSERT INTO `t_goods` VALUES ('15', '商品15', '【米哈游/未定事件簿】心动档案系列 马口铁徽章 miHoYo', 'O1CN014Vnotg1fEb8u3su1p_!!0-item_pic.jpg_300x300q90.jpg', '无', '15.00', '958');
INSERT INTO `t_goods` VALUES ('16', '商品16', '西装外套女春秋休闲小西服kbne2023春装新款韩版宽松小个子上衣', 'O1CN01d4TRve1tUzzAnO64c_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '439.00', '240');
INSERT INTO `t_goods` VALUES ('17', '商品17', '淡颜系多汁茶冻子~镜面水光唇釉茶冻水唇蜜蜜豆红茶嘟嘟唇学生党', 'O1CN010zRvuU26HnYG3JEau_!!0-item_pic.jpg_300x300q90.jpg', '无', '13.80', '311');
INSERT INTO `t_goods` VALUES ('18', '商品18', 'babylove新生儿抱被春秋纯棉双层包被初生兔年宝宝襁褓巾婴儿抱毯', 'O1CN01xtldHA1G7OYkooOHN_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '89.00', '822');
INSERT INTO `t_goods` VALUES ('19', '商品19', '双头唇釉镜面日系灰粉色唇泥蜜桃粉唇彩低饱和裸色系口红平价学生', 'O1CN01hofq9b28MUnX88PC6_!!266427918.jpg_300x300q90.jpg', '无', '5.70', '161');
INSERT INTO `t_goods` VALUES ('20', '商品20', 'O\'2nd/奥蔻春季复刻款韩风假领子大翻领字母刺绣假两件上衣', 'O1CN01eJNHAP1HCikOy2t2A_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '1580.00', '326');
INSERT INTO `t_goods` VALUES ('21', '商品21', 'Canon/佳能ixus数码相机学生CCD复古照相机老式索尼卡片机胶片感', 'O1CN01bcN6Vp1ps7VGmaivv_!!2212950945415.jpg_300x300q90.jpg', '无', '255.00', '133');
INSERT INTO `t_goods` VALUES ('22', '商品22', '瑞士名牌18k金色男士手表机械表全自动陀飞轮镂空防水正品牌十大', 'O1CN01KSaO8Z1Cgskuk0UyF_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '398.00', '673');
INSERT INTO `t_goods` VALUES ('23', '商品23', '歌剧遮瑕膏小样魅影遮瑕盘中国限定三色6号1号六色遮黑眼圈泪沟', 'O1CN01lSxSRt2MznTQEfsob_!!2245339899-0-picasso.jpg_300x300q90.jpg', '无', '25.00', '949');
INSERT INTO `t_goods` VALUES ('24', '商品24', '粉色卫衣女2022新款正肩闺蜜装韩版宽松连帽春秋薄款休闲上衣外套', 'O1CN01DUtSqU1Z7NC2NgabV_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '99.90', '714');
INSERT INTO `t_goods` VALUES ('25', '商品25', 'Coca-Cola可口可乐香港版柠檬可乐碳酸饮料汽水网红易拉罐气泡水', 'O1CN0173iskb1d3wjwJvI4L_!!729403681.jpg_300x300q90.jpg', '无', '26.80', '706');
INSERT INTO `t_goods` VALUES ('26', '商品26', '专用于广汽埃安SPLUS改装车窗饰条aionsplus', 'O1CN01eTqAra21mPsiOWnRY_!!1118327027.jpg_300x300q90.jpg', '无', '268.00', '374');
INSERT INTO `t_goods` VALUES ('27', '商品27', '韩国新款NY托特包女洋基队复古老花购物袋手提单肩大包学生通勤包', 'O1CN01feI3O926XN1h4Wi0p_!!2214907671.jpg_300x300q90.jpg', '无', '128.00', '740');
INSERT INTO `t_goods` VALUES ('28', '商品28', '妈妈纯棉长袖t恤女2023年新款春秋内大码中年女士秋衣打底衫上衣', 'O1CN01RaT5Qr1fK5v99pZS9_!!3576743987-0-alimamacc.jpg_300x300q90.jpg', '无', '89.00', '562');
INSERT INTO `t_goods` VALUES ('29', '商品29', 'cleanclear可伶可俐吸油纸面部控油女男学生脸部面巾纸吸油膜便携', 'O1CN01lCzqIt1WKFWjP4kKT_!!0-item_pic.jpg_300x300q90.jpg', '无', '15.90', '575');
INSERT INTO `t_goods` VALUES ('30', '商品30', 'M家潮牌白色运动服套装女酷盐系炸街春秋休闲宽松卫衣裤子两件套', 'O1CN01DtbkHL1xdThjTslPZ_!!97456466.jpg_300x300q90.jpg', '无', '138.00', '176');
INSERT INTO `t_goods` VALUES ('31', '商品31', '可爱柴犬屁股抱枕靠垫坐垫软体狗狗玩偶儿童穿上睡觉头枕午睡枕头', 'O1CN01nwQmpR1l9ulE8G9M8_!!0-item_pic.jpg_300x300q90.jpg', '无', '9.90', '139');
INSERT INTO `t_goods` VALUES ('32', '商品32', '新中式座椅简约铁艺餐椅靠背椅包厢椅餐桌椅酒店餐厅饭店商用椅子', 'O1CN016QJrx824MialQ8Am6_!!1750657377.jpg_300x300q90.jpg', '无', '136.00', '155');
INSERT INTO `t_goods` VALUES ('33', '商品33', '外盒破损链接(二)正品特价清仓临期折损品girlcult 万花镜 hedone', 'O1CN01v2WPhV1DYvTCO4fL3_!!676740229.jpg_300x300q90.jpg', '无', '65.00', '341');
INSERT INTO `t_goods` VALUES ('34', '商品34', '桃核雕精新品经典八仙法器手牌手串纯手工秦岭蟠桃胡男女手链', 'O1CN01Eo37Fb20R3sUF1pAC_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '438.00', '226');
INSERT INTO `t_goods` VALUES ('35', '商品35', '【米哈游/未定事件簿】燃情悸动系列 马口铁徽章 miHoYo', 'O1CN017edDLM1fEbApVL7bG_!!0-item_pic.jpg_300x300q90.jpg', '无', '15.00', '92');
INSERT INTO `t_goods` VALUES ('36', '商品36', '卡其色a字半身裙女春夏2022新款时尚职业裙子高腰显瘦中长款半裙', 'O1CN01pw6fbD1C27NDJrwph_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '168.00', '767');
INSERT INTO `t_goods` VALUES ('37', '商品37', 'drew潮牌笑脸字母短袖t恤女欧美高街宽松情侣半袖纯棉体恤男INS潮', 'O1CN01iBZiiP1Q1ZfWOvazD_!!150771916.jpg_300x300q90.jpg', '无', '59.00', '546');
INSERT INTO `t_goods` VALUES ('38', '商品38', '红香蕉新鲜水果红贵妃斤红蕉美人蕉香甜红皮蕉现摘', 'O1CN01OVVuHd1IqOQ4xUXdg_!!81630944.jpg_300x300q90.jpg', '无', '21.80', '413');
INSERT INTO `t_goods` VALUES ('39', '商品39', '小蓝盾粉底液 持久控油不易脱妆油皮亲妈遮瑕隐形毛孔 女学生平价', 'O1CN01HIuBvs2DwHlFVYoX3_!!925778673.jpg_300x300q90.jpg', '无', '9.60', '413');
INSERT INTO `t_goods` VALUES ('40', '商品40', '阿宽粉耗子6袋装正宗东北土豆粉米粉粗粉条方便速食甜辣网红拌粉', 'O1CN015cL19S1pAbpISxklX_!!0-item_pic.jpg_300x300q90.jpg', '无', '9.90', '811');
INSERT INTO `t_goods` VALUES ('41', '商品41', '可爱百搭帆布大容量托特单肩包包女2023新款学生上课通勤手提包', 'O1CN01ZfJz3L1XjGkfguKX9_!!2201253122959.jpg_300x300q90.jpg', '无', '29.90', '802');
INSERT INTO `t_goods` VALUES ('42', '商品42', '【百亿补贴】盐津铺子零食大礼包休闲食品小吃晚上解饿小零食大全', 'O1CN01g1tT6F1sFb3IUNzDM_!!0-item_pic.jpg_300x300q90.jpg', '无', '11.90', '562');
INSERT INTO `t_goods` VALUES ('43', '商品43', '2023年夏季情侣短袖t恤女字母上衣ins美式复古oversize超火半袖潮', 'O1CN01Zv5m1N1Id74aUBv4T_!!0-item_pic.jpg_300x300q90.jpg', '无', '23.80', '388');
INSERT INTO `t_goods` VALUES ('44', '商品44', '大豆纤维床垫软垫学生宿舍单人垫褥褥子垫被家用床褥租房专用地铺', 'O1CN01pVfvH41hwE8LksYjD_!!0-item_pic.jpg_300x300q90.jpg', '无', '158.00', '242');
INSERT INTO `t_goods` VALUES ('45', '商品45', '迪卡侬官方旗舰店官网儿童运动背包轻便登山包双肩包学生书包KIDD', 'O1CN01CLngId2GbcocK8H8C_!!0-item_pic.jpg_300x300q90.jpg', '无', '49.90', '30');
INSERT INTO `t_goods` VALUES ('46', '商品46', '重磅不透！简约小人涂鸦hiphop嘻哈小众设计男女健身情侣短袖T恤', 'O1CN01RQUrIn1NjaszUG88n_!!1093861606.jpg_300x300q90.jpg', '无', '39.80', '412');
INSERT INTO `t_goods` VALUES ('47', '商品47', 'From Mars星移月明 日系简约星星字母刺绣短袖男女ins宽松百搭T恤', 'O1CN011sG3mh1M7kXvOnmRC_!!2130151388.jpg_300x300q90.jpg', '无', '69.00', '954');
INSERT INTO `t_goods` VALUES ('48', '商品48', 'Hello Kitty和她的小伙伴们 闪闪亮小粉书系列 3-6岁 三丽鸥股份有限公司 著 手工 游戏', 'O1CN014gvwqu1XpgGQZaRJa_!!0-item_pic.jpg_300x300q90.jpg', '无', '21.00', '520');
INSERT INTO `t_goods` VALUES ('49', '商品49', '数码相机佳能ixus155学生款复古CCD入门级索尼高清自拍vlog卡片机', 'O1CN01Uz2LwI26nrHdtBuo7_!!2201216487707.jpg_300x300q90.jpg', '无', '220.00', '721');
INSERT INTO `t_goods` VALUES ('50', '商品50', '说不出的喜欢！米白奶杏色华夫格长袖T恤男藏蓝宽松内搭打底衫女', 'O1CN01rjejO11Frp1x5IGFJ_!!0-item_pic.jpg_300x300q90.jpg', '无', '59.00', '33');
INSERT INTO `t_goods` VALUES ('51', '商品51', '奥特莱斯品牌折扣店撤柜清仓捡漏轻奢五珠闪钻手链outlets女配饰', 'O1CN01vNLNid1Lgj7u1dpgq_!!3690321329.jpg_300x300q90.jpg', '无', '9.90', '987');
INSERT INTO `t_goods` VALUES ('52', '商品52', '宝藏单品！会拉丝！扁桃仁夹心酥牛轧扎坚果饼干薄脆网红零食米惦', 'O1CN01ABqHLu2Do2lcUWkCo_!!0-item_pic.jpg_300x300q90.jpg', '无', '28.80', '821');
INSERT INTO `t_goods` VALUES ('53', '商品53', 'GRAF原创品牌BooGhost可爱鬼2.0汉堡火山天使毛绒钥匙扣挂件包挂', 'O1CN01m5uHYj1biaaxrIdXm_!!112093499.png_300x300q90.jpg', '无', '48.00', '128');
INSERT INTO `t_goods` VALUES ('54', '商品54', '塔尖麦穗款恶魔假睫毛自然仿真女洛兜分段式单簇浓密睫毛仙子毛', 'O1CN019juy7N1JYMgzMX48f_!!2211581851040.jpg_300x300q90.jpg', '无', '17.80', '166');
INSERT INTO `t_goods` VALUES ('55', '商品55', '好丽友果滋果心软糖荔枝软糖水果果汁爆浆糖果剥皮软糖果零食喜糖', 'O1CN016gxsMW1T1yr6IrGlU_!!2209273402323.jpg_300x300q90.jpg', '无', '14.70', '428');
INSERT INTO `t_goods` VALUES ('56', '商品56', '绝美奶fufu连衣裙小众设计枯玫瑰裙初恋清纯奶甜裙子仙女超仙森系', 'O1CN01B0Y5qt1Nky57CB3kA_!!2208236021609.jpg_300x300q90.jpg', '无', '88.99', '630');
INSERT INTO `t_goods` VALUES ('57', '商品57', 'Dills kill The Powerpuff Girls飞天小女警童趣大容量手提袋包包', 'O1CN01wCVtfj1dCBqqPS7Yd_!!2214192873699.jpg_300x300q90.jpg', '无', '30.06', '852');
INSERT INTO `t_goods` VALUES ('58', '商品58', '电煮锅宿舍学生小电锅多功能迷你泡面锅小型电热火锅单人一人家用', 'O1CN01BoD7zF1pn59BdGUlF_!!0-item_pic.jpg_300x300q90.jpg', '无', '24.90', '355');
INSERT INTO `t_goods` VALUES ('59', '商品59', '【周边】对角巷 绘旅人 白色情人节 艾因/叶瑄 谷美 教堂/拱门', 'O1CN01R0w8YH2DLBoJs4cVE_!!326568592.png_300x300q90.jpg', '无', '14.00', '205');
INSERT INTO `t_goods` VALUES ('60', '商品60', '孕味食足海苔即食芝麻夹心海苔脆海味紫菜休闲孕妇儿童小零食', 'O1CN01r6EAaf1IbHdZSf1wa_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '15.90', '947');
INSERT INTO `t_goods` VALUES ('61', '商品61', '七天挑战廋15斤 白芸豆黑咖啡 断油黑科技 空姐超模内部专用 ！', 'O1CN01qIb3CX2MHN2sgR0gE_!!0-item_pic.jpg_300x300q90.jpg', '无', '9.90', '103');
INSERT INTO `t_goods` VALUES ('62', '商品62', 'SIAMOISES2022欧美新款超高腰阔腿牛仔短裤女显瘦毛边宽松夏季潮', 'TB2dTyaeoR1BeNjy0FmXXb0wVXa_!!2589120327.jpg_300x300q90.jpg', '无', '199.00', '662');
INSERT INTO `t_goods` VALUES ('63', '商品63', '2双装 压力强压瘦腿型运动专业速干小腿款~', 'O1CN01wXLB2m1DLe10H5jyL_!!2214998370200.jpg_300x300q90.jpg', '无', '9.90', '987');
INSERT INTO `t_goods` VALUES ('64', '商品64', '陌遇2023夏季新款中国风汉元素改良交领真丝绣花新中式气质连衣裙', 'O1CN019AQDRX1CO6XPeRerB_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '998.00', '932');
INSERT INTO `t_goods` VALUES ('65', '商品65', 'A类双层纱床上四件套春卡通床单被套罩学生宿舍三件套非纯棉全棉', 'O1CN019IvFHE1EUB1BOWeHO_!!0-item_pic.jpg_300x300q90.jpg', '无', '78.00', '684');
INSERT INTO `t_goods` VALUES ('66', '商品66', '厨房防火防油防潮贴纸防水耐高温灶柜台用锡纸铝箔纸自粘墙贴壁纸', 'O1CN01hnjXbw1KKSGoBOavt_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '4.90', '609');
INSERT INTO `t_goods` VALUES ('67', '商品67', '7天挑战廋20斤 懒人福音 胖子常备 女神悄悄变 模特内部使用！！', 'O1CN01PuEqlp2AReWp4fzof_!!2-item_pic.png_300x300q90.jpg', '无', '19.90', '979');
INSERT INTO `t_goods` VALUES ('68', '商品68', '金晔小牛原味陈皮蓝莓山楂棒棒糖儿童宝宝休闲零食食品无添加500g', 'O1CN01qeI1ZJ1bTTaEdgk8f_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '9.90', '56');
INSERT INTO `t_goods` VALUES ('69', '商品69', '400张抽纸大包整箱家庭实惠装卫生纸家用餐巾纸面巾纸擦手纸纸巾', 'O1CN01QF4xcE1r7WRCziLm2_!!0-item_pic.jpg_300x300q90.jpg', '无', '5.10', '326');
INSERT INTO `t_goods` VALUES ('70', '商品70', '轩铭堂 纯铜茶洗 手工日式锤纹紫铜特大号杯洗水盂建水笔洗茶渣桶', 'O1CN011CNdlnWbEjB75fk_!!245310069.jpg_300x300q90.jpg', '无', '368.00', '447');
INSERT INTO `t_goods` VALUES ('71', '商品71', 'u试先用大牌阿尼红唇釉中小样女哑光丝绒雾面u先优先试用正品入口', 'O1CN01jfWrsE2KQPcEBTLgH_!!0-item_pic.jpg_300x300q90.jpg', '无', '10.80', '243');
INSERT INTO `t_goods` VALUES ('72', '商品72', 'O\'2nd/奥蔻2023春夏新款甜美学院风针织百褶短裙A字裙', 'O1CN01ReB90L1HCim2Utz4X_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '2280.00', '860');
INSERT INTO `t_goods` VALUES ('73', '商品73', '玉米须茶官方旗舰店正品苦荞栀子茶消去降养生水肿泡水喝的茶包茶', 'O1CN01fOEEoB1b9mVIhYcE0_!!0-item_pic.jpg_300x300q90.jpg', '无', '10.00', '558');
INSERT INTO `t_goods` VALUES ('74', '商品74', '奥蒂慕2023年春季新款夹克中年男士商务休闲男装爸爸春秋连帽外套', 'O1CN0149BM4V1IBB3IRsvPG_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '268.00', '193');
INSERT INTO `t_goods` VALUES ('75', '商品75', '奶油紫！赵露思同款斜跨大容量包包女时尚手提托特包尼龙布单肩包', 'O1CN01Qp4tET1dqUfu3GG22_!!896113787.jpg_300x300q90.jpg', '无', '19.50', '278');
INSERT INTO `t_goods` VALUES ('76', '商品76', '糖蒜正宗糖醋蒜蒜头腌制糖醋大蒜头甜蒜泡蒜大蒜腊八蒜咸菜下饭菜', 'O1CN01i9gAhP1u7vcSfINxx_!!2206426605991.jpg_300x300q90.jpg', '无', '11.80', '796');
INSERT INTO `t_goods` VALUES ('77', '商品77', '全脑开发贴贴画专注力训练儿童贴纸书益智玩具2-3-5-6岁4男女孩贴贴纸幼儿园宝宝换装/汽车/恐龙/蒙氏数学/童话故事贴画书早教启蒙', 'O1CN01IzBCdZ1Oy4vua0iBm_!!0-item_pic.jpg_300x300q90.jpg', '无', '12.90', '130');
INSERT INTO `t_goods` VALUES ('78', '商品78', '六角玻璃泡茶壶木把电陶炉煮茶壶带过滤茶水分离沏茶壶家用花茶壶', 'O1CN01cT7JQj1CqxTUXagHR_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '58.00', '248');
INSERT INTO `t_goods` VALUES ('79', '商品79', '可爱日系防水围裙防油家用厨房做饭时尚男女围腰工作服定制印logo', 'O1CN019UYrSm1a3XOOUrSoL_!!0-item_pic.jpg_300x300q90.jpg', '无', '7.80', '835');
INSERT INTO `t_goods` VALUES ('80', '商品80', '鸿章万兆有源AOC光缆SFP+10G-AOC-OM3堆叠线', 'O1CN01YzBEgg26MpgniUxkA_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '85.00', '416');
INSERT INTO `t_goods` VALUES ('81', '商品81', '德佑乳霜柔纸巾婴儿保湿纸巾宝宝专用超柔抽纸新生儿云柔巾鼻子纸', 'O1CN01LmVi3H1a3zvPrizNA_!!0-item_pic.jpg_300x300q90.jpg', '无', '11.90', '563');
INSERT INTO `t_goods` VALUES ('82', '商品82', '2023春夏新款尖头细跟水钻搭口露趾一字凉鞋时尚性感高跟鞋杨树林', 'O1CN01nRxkct1C84fixNFoW_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '158.00', '550');
INSERT INTO `t_goods` VALUES ('83', '商品83', '金银花决明子竹叶菊花茶清热去火排明目熬夜肝养茶毒生护恢复花茶', 'O1CN01xQTgC51aZbDCrpVmD_!!0-item_pic.jpg_300x300q90.jpg', '无', '14.90', '49');
INSERT INTO `t_goods` VALUES ('84', '商品84', '泳衣女分体夏长袖防晒遮肚显瘦保守学生温泉2022新爆款两件套遮肉', 'O1CN01cSP3K821EWdFRaDwm_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '125.00', '581');
INSERT INTO `t_goods` VALUES ('85', '商品85', '7天挑战瘦20斤 懒人福音 胖子常备 断油防弹黑咖啡燃无糖阻脂饱腹', 'O1CN01otECb02I0BjISCafS_!!0-item_pic.jpg_300x300q90.jpg', '无', '18.00', '741');
INSERT INTO `t_goods` VALUES ('86', '商品86', '韩国316不锈钢饭盒便当盒儿童分格餐盘小学生专用保温带盖午餐盒', 'O1CN01vw9TvC1DlI8aRAQYk_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '98.00', '947');
INSERT INTO `t_goods` VALUES ('87', '商品87', '多肉植物精品组合盆栽绿植花卉桃蛋玉露熊童子红宝石老桩带盆易活', 'O1CN01UlpoIR1ryeM2i1aHL_!!0-item_pic.jpg_300x300q90.jpg', '无', '11.90', '499');
INSERT INTO `t_goods` VALUES ('88', '商品88', '30%甲霜恶霉灵立枯病枯萎病根腐病烂根专用药果树蔬菜土壤杀菌剂', 'O1CN017S6GJI1D12AB3JSkl_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '44.00', '637');
INSERT INTO `t_goods` VALUES ('89', '商品89', '李佳琪推荐婴儿体香宝宝香水少女清新持久淡香学生女士香水自然', 'O1CN01m7pHcD1eOqK5pULaX_!!0-item_pic.jpg_300x300q90.jpg', '无', '10.80', '676');
INSERT INTO `t_goods` VALUES ('90', '商品90', '宝马车载香薰3系5系7系五ix3x4x5x1x7专用BMW香氛系统负离子原厂', 'O1CN01Qsv5801oMEFvUuSDl_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '580.00', '453');
INSERT INTO `t_goods` VALUES ('91', '商品91', '3包|洗脸巾一次性棉柔巾男女洗面擦脸巾干抽取式洁面纯加厚家庭装', 'O1CN01qJeMiW1okVEVwy84B_!!0-item_pic.jpg_300x300q90.jpg', '无', '8.80', '221');
INSERT INTO `t_goods` VALUES ('92', '商品92', '哺乳文胸软钢圈定型聚拢防下垂孕妇内衣大胸', 'O1CN01XtwSzp21hNR9pvjmC_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '109.00', '733');
INSERT INTO `t_goods` VALUES ('93', '商品93', '趣味卡通可爱动物适用iphone14promax苹果13手机壳创意11新款14pro女款12小众防摔硅胶13pro全包保护套男生软', 'O1CN01iXAcMz1kGxHi71tnR_!!0-item_pic.jpg_300x300q90.jpg', '无', '12.90', '987');
INSERT INTO `t_goods` VALUES ('94', '商品94', '手扶箱垫外贸汽车扶手箱垫通用型车内装饰品车载中央手扶箱质量好', 'O1CN01ecN7Aa1DC1fg32Z98_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '16.50', '720');
INSERT INTO `t_goods` VALUES ('95', '商品95', '亚麻拖鞋室内居家四季女生厚底可爱公主风春秋防滑踩屎感网红超火', 'O1CN01DowW8J1Wsb8zXEOR3_!!2212705662844.jpg_300x300q90.jpg', '无', '29.80', '627');
INSERT INTO `t_goods` VALUES ('96', '商品96', '钮子开关 摇头摇臂开关 MTS-102 103 202无/', 'O1CN01VOnlNp1D2rfz4zkPE_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '1.89', '958');
INSERT INTO `t_goods` VALUES ('97', '商品97', '日本vegiebag妈咪包手提母婴包外出轻便大容量妈妈包女帆布托特包', 'O1CN01iz9KqD1V5lKkET6Ox_!!763842602.jpg_300x300q90.jpg', '无', '29.90', '896');
INSERT INTO `t_goods` VALUES ('98', '商品98', '小个子紫色气质连衣裙女装春季别致设计紧身包臀短裙子2023年新款', 'O1CN01zj9qLA1CtFTIyhfM7_!!0-saturn_solar.jpg_300x300q90.jpg', '无', '188.88', '591');
INSERT INTO `t_goods` VALUES ('99', '商品99', '搓澡巾儿童搓澡神器强力搓灰搓泥女士不疼无痛婴儿宝宝洗澡海绵男', 'O1CN01yeK2sE1QeV8x5WB6j_!!0-item_pic.jpg_300x300q90.jpg', '无', '4.90', '251');
