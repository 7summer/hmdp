/*
 Navicat Premium Data Transfer

 Source Server         : quake
 Source Server Type    : MySQL
 Source Server Version : 80034
 Source Host           : localhost:3306
 Source Schema         : hmdp

 Target Server Type    : MySQL
 Target Server Version : 80034
 File Encoding         : 65001

 Date: 02/06/2024 10:22:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_blog
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog`;
CREATE TABLE `tb_blog`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `shop_id` bigint(0) NOT NULL COMMENT '商户id',
  `user_id` bigint(0) UNSIGNED NOT NULL COMMENT '用户id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `images` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '探店的照片，最多9张，多张以\",\"隔开',
  `content` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '探店的文字描述',
  `liked` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '点赞数量',
  `comments` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '评论数量',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of tb_blog
-- ----------------------------
INSERT INTO `tb_blog` VALUES (4, 4, 2, '无尽浪漫的夜晚丨在万花丛中摇晃着红酒杯🍷品战斧牛排🥩', '/imgs/blogs/7/14/4771fefb-1a87-4252-816c-9f7ec41ffa4a.jpg,/imgs/blogs/4/10/2f07e3c9-ddce-482d-9ea7-c21450f8d7cd.jpg,/imgs/blogs/2/6/b0756279-65da-4f2d-b62a-33f74b06454a.jpg,/imgs/blogs/10/7/7e97f47d-eb49-4dc9-a583-95faa7aed287.jpg,/imgs/blogs/1/2/4a7b496b-2a08-4af7-aa95-df2c3bd0ef97.jpg,/imgs/blogs/14/3/52b290eb-8b5d-403b-8373-ba0bb856d18e.jpg', '生活就是一半烟火·一半诗意<br/>手执烟火谋生活·心怀诗意以谋爱·<br/>当然<br/>\r\n男朋友给不了的浪漫要学会自己给🍒<br/>\n无法重来的一生·尽量快乐.<br/><br/>🏰「小筑里·神秘浪漫花园餐厅」🏰<br/><br/>\n💯这是一家最最最美花园的西餐厅·到处都是花餐桌上是花前台是花  美好无处不在\n品一口葡萄酒，维亚红酒马瑟兰·微醺上头工作的疲惫消失无际·生如此多娇🍃<br/><br/>📍地址:延安路200号(家乐福面)<br/><br/>🚌交通:地铁①号线定安路B口出右转过下通道右转就到啦～<br/><br/>--------------🥣菜品详情🥣---------------<br/><br/>「战斧牛排]<br/>\n超大一块战斧牛排经过火焰的炙烤发出阵阵香，外焦里嫩让人垂涎欲滴，切开牛排的那一刻，牛排的汁水顺势流了出来，分熟的牛排肉质软，简直细嫩到犯规，一刻都等不了要放入嘴里咀嚼～<br/><br/>「奶油培根意面」<br/>太太太好吃了💯<br/>我真的无法形容它的美妙，意面混合奶油香菇的香味真的太太太香了，我真的舔盘了，一丁点美味都不想浪费‼️<br/><br/><br/>「香菜汁烤鲈鱼」<br/>这个酱是辣的 真的绝好吃‼️<br/>鲈鱼本身就很嫩没什么刺，烤过之后外皮酥酥的，鱼肉蘸上酱料根本停不下来啊啊啊啊<br/>能吃辣椒的小伙伴一定要尝尝<br/><br/>非常可 好吃子🍽\n<br/>--------------🍃个人感受🍃---------------<br/><br/>【👩🏻‍🍳服务】<br/>小姐姐特别耐心的给我们介绍彩票 <br/>推荐特色菜品，拍照需要帮忙也是尽心尽力配合，太爱他们了<br/><br/>【🍃环境】<br/>比较有格调的西餐厅 整个餐厅的布局可称得上的万花丛生 有种在人间仙境的感觉🌸<br/>集美食美酒与鲜花为一体的风格店铺 令人向往<br/>烟火皆是生活 人间皆是浪漫<br/>', 3, 104, '2021-12-28 19:50:01', '2024-04-19 23:22:59');
INSERT INTO `tb_blog` VALUES (5, 1, 2, '人均30💰杭州这家港式茶餐厅我疯狂打call‼️', '/imgs/blogs/4/7/863cc302-d150-420d-a596-b16e9232a1a6.jpg,/imgs/blogs/11/12/8b37d208-9414-4e78-b065-9199647bb3e3.jpg,/imgs/blogs/4/1/fa74a6d6-3026-4cb7-b0b6-35abb1e52d11.jpg,/imgs/blogs/9/12/ac2ce2fb-0605-4f14-82cc-c962b8c86688.jpg,/imgs/blogs/4/0/26a7cd7e-6320-432c-a0b4-1b7418f45ec7.jpg,/imgs/blogs/15/9/cea51d9b-ac15-49f6-b9f1-9cf81e9b9c85.jpg', '又吃到一家好吃的茶餐厅🍴环境是怀旧tvb港风📺边吃边拍照片📷几十种菜品均价都在20+💰可以是很平价了！<br>·<br>店名：九记冰厅(远洋店)<br>地址：杭州市丽水路远洋乐堤港负一楼（溜冰场旁边）<br>·<br>✔️黯然销魂饭（38💰）<br>这碗饭我吹爆！米饭上盖满了甜甜的叉烧 还有两颗溏心蛋🍳每一粒米饭都裹着浓郁的酱汁 光盘了<br>·<br>✔️铜锣湾漏奶华（28💰）<br>黄油吐司烤的脆脆的 上面洒满了可可粉🍫一刀切开 奶盖流心像瀑布一样流出来  满足<br>·<br>✔️神仙一口西多士士（16💰）<br>简简单单却超级好吃！西多士烤的很脆 黄油味浓郁 面包体超级柔软 上面淋了炼乳<br>·<br>✔️怀旧五柳炸蛋饭（28💰）<br>四个鸡蛋炸成蓬松的炸蛋！也太好吃了吧！还有大块鸡排 上淋了酸甜的酱汁 太合我胃口了！！<br>·<br>✔️烧味双拼例牌（66💰）<br>选了烧鹅➕叉烧 他家烧腊品质真的惊艳到我！据说是每日广州发货 到店现烧现卖的黑棕鹅 每口都是正宗的味道！肉质很嫩 皮超级超级酥脆！一口爆油！叉烧肉也一点都不柴 甜甜的很入味 搭配梅子酱很解腻 ！<br>·<br>✔️红烧脆皮乳鸽（18.8💰）<br>乳鸽很大只 这个价格也太划算了吧， 肉质很有嚼劲 脆皮很酥 越吃越香～<br>·<br>✔️大满足小吃拼盘（25💰）<br>翅尖➕咖喱鱼蛋➕蝴蝶虾➕盐酥鸡<br>zui喜欢里面的咖喱鱼！咖喱酱香甜浓郁！鱼蛋很q弹～<br>·<br>✔️港式熊仔丝袜奶茶（19💰）<br>小熊🐻造型的奶茶冰也太可爱了！颜值担当 很地道的丝袜奶茶 茶味特别浓郁～<br>·', 3, 0, '2021-12-28 20:57:49', '2024-04-20 17:06:36');
INSERT INTO `tb_blog` VALUES (6, 10, 1, '杭州周末好去处｜💰50就可以骑马啦🐎', '/imgs/blogs/blog1.jpg', '杭州周末好去处｜💰50就可以骑马啦🐎', 1, 0, '2022-01-11 16:05:47', '2022-03-10 09:21:41');
INSERT INTO `tb_blog` VALUES (7, 10, 1, '杭州周末好去处｜💰50就可以骑马啦🐎', '/imgs/blogs/blog1.jpg', '杭州周末好去处｜💰50就可以骑马啦🐎', 1, 0, '2022-01-11 16:05:47', '2022-03-10 09:21:42');
INSERT INTO `tb_blog` VALUES (24, 1, 2, '分享鹿鹿', '/imgs/blogs/13/5/0050782f-54c7-4ef0-9088-f739f4ce1eef.jpeg', '鹿依心飞，茸光相随', 0, NULL, '2024-04-20 17:17:42', '2024-04-20 17:17:42');
INSERT INTO `tb_blog` VALUES (25, 2, 2, '再次分享鹿鹿', '/imgs/blogs/14/13/189c1938-b36d-4781-82cd-da2ed744a49a.jpg', '她真的好好笑啊', 1, NULL, '2024-04-20 17:41:37', '2024-04-21 14:46:45');

-- ----------------------------
-- Table structure for tb_blog_comments
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_comments`;
CREATE TABLE `tb_blog_comments`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(0) UNSIGNED NOT NULL COMMENT '用户id',
  `blog_id` bigint(0) UNSIGNED NOT NULL COMMENT '探店id',
  `parent_id` bigint(0) UNSIGNED NOT NULL COMMENT '关联的1级评论id，如果是一级评论，则值为0',
  `answer_id` bigint(0) UNSIGNED NOT NULL COMMENT '回复的评论id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复的内容',
  `liked` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '点赞数',
  `status` tinyint(0) UNSIGNED NULL DEFAULT NULL COMMENT '状态，0：正常，1：被举报，2：禁止查看',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of tb_blog_comments
-- ----------------------------

-- ----------------------------
-- Table structure for tb_follow
-- ----------------------------
DROP TABLE IF EXISTS `tb_follow`;
CREATE TABLE `tb_follow`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(0) UNSIGNED NOT NULL COMMENT '用户id',
  `follow_user_id` bigint(0) UNSIGNED NOT NULL COMMENT '关联的用户id',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of tb_follow
-- ----------------------------
INSERT INTO `tb_follow` VALUES (2, 1010, 2, '2024-04-20 00:05:25');
INSERT INTO `tb_follow` VALUES (3, 1010, 1, '2024-04-20 10:33:43');
INSERT INTO `tb_follow` VALUES (4, 1, 2, '2024-04-20 10:33:53');
INSERT INTO `tb_follow` VALUES (5, 2, 1, '2024-04-20 11:00:40');

-- ----------------------------
-- Table structure for tb_seckill_voucher
-- ----------------------------
DROP TABLE IF EXISTS `tb_seckill_voucher`;
CREATE TABLE `tb_seckill_voucher`  (
  `voucher_id` bigint(0) UNSIGNED NOT NULL COMMENT '关联的优惠券的id',
  `stock` int(0) NOT NULL COMMENT '库存',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `begin_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '生效时间',
  `end_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '失效时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`voucher_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '秒杀优惠券表，与优惠券是一对一关系' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of tb_seckill_voucher
-- ----------------------------
INSERT INTO `tb_seckill_voucher` VALUES (2, 0, '2024-04-13 09:54:12', '2024-04-15 10:42:39', '2024-04-20 09:42:39', '2024-04-16 17:12:05');
INSERT INTO `tb_seckill_voucher` VALUES (15, 0, '2024-04-18 15:30:36', '2024-04-16 10:42:39', '2024-04-20 09:42:39', '2024-04-18 15:37:58');

-- ----------------------------
-- Table structure for tb_shop
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop`;
CREATE TABLE `tb_shop`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商铺名称',
  `type_id` bigint(0) UNSIGNED NOT NULL COMMENT '商铺类型的id',
  `images` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商铺图片，多个图片以\',\'隔开',
  `area` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商圈，例如陆家嘴',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '地址',
  `x` double UNSIGNED NOT NULL COMMENT '经度',
  `y` double UNSIGNED NOT NULL COMMENT '维度',
  `avg_price` bigint(0) UNSIGNED NULL DEFAULT NULL COMMENT '均价，取整数',
  `sold` int(10) UNSIGNED ZEROFILL NOT NULL COMMENT '销量',
  `comments` int(10) UNSIGNED ZEROFILL NOT NULL COMMENT '评论数量',
  `score` int(2) UNSIGNED ZEROFILL NOT NULL COMMENT '评分，1~5分，乘10保存，避免小数',
  `open_hours` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业时间，例如 10:00-22:00',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `foreign_key_type`(`type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of tb_shop
-- ----------------------------
INSERT INTO `tb_shop` VALUES (1, '103茶餐厅', 1, 'https://qcloud.dpfile.com/pc/jiclIsCKmOI2arxKN1Uf0Hx3PucIJH8q0QSz-Z8llzcN56-_QiKuOvyio1OOxsRtFoXqu0G3iT2T27qat3WhLVEuLYk00OmSS1IdNpm8K8sG4JN9RIm2mTKcbLtc2o2vfCF2ubeXzk49OsGrXt_KYDCngOyCwZK-s3fqawWswzk.jpg,https://qcloud.dpfile.com/pc/IOf6VX3qaBgFXFVgp75w-KKJmWZjFc8GXDU8g9bQC6YGCpAmG00QbfT4vCCBj7njuzFvxlbkWx5uwqY2qcjixFEuLYk00OmSS1IdNpm8K8sG4JN9RIm2mTKcbLtc2o2vmIU_8ZGOT1OjpJmLxG6urQ.jpg', '大关', '金华路锦昌文华苑29号', 120.149192, 30.316078, 80, 0000004215, 0000003035, 37, '10:00-22:00', '2021-12-22 18:10:39', '2024-04-11 23:41:48');
INSERT INTO `tb_shop` VALUES (2, '蔡馬洪涛烤肉·老北京铜锅涮羊肉', 1, 'https://p0.meituan.net/bbia/c1870d570e73accbc9fee90b48faca41195272.jpg,http://p0.meituan.net/mogu/397e40c28fc87715b3d5435710a9f88d706914.jpg,https://qcloud.dpfile.com/pc/MZTdRDqCZdbPDUO0Hk6lZENRKzpKRF7kavrkEI99OxqBZTzPfIxa5E33gBfGouhFuzFvxlbkWx5uwqY2qcjixFEuLYk00OmSS1IdNpm8K8sG4JN9RIm2mTKcbLtc2o2vmIU_8ZGOT1OjpJmLxG6urQ.jpg', '拱宸桥/上塘', '上塘路1035号（中国工商银行旁）', 120.151505, 30.333422, 85, 0000002160, 0000001460, 46, '11:30-03:00', '2021-12-22 19:00:13', '2022-01-11 16:12:26');
INSERT INTO `tb_shop` VALUES (3, '新白鹿餐厅(运河上街店)', 1, 'https://p0.meituan.net/biztone/694233_1619500156517.jpeg,https://img.meituan.net/msmerchant/876ca8983f7395556eda9ceb064e6bc51840883.png,https://img.meituan.net/msmerchant/86a76ed53c28eff709a36099aefe28b51554088.png', '运河上街', '台州路2号运河上街购物中心F5', 120.151954, 30.32497, 61, 0000012035, 0000008045, 47, '10:30-21:00', '2021-12-22 19:10:05', '2022-01-11 16:12:42');
INSERT INTO `tb_shop` VALUES (4, 'Mamala(杭州远洋乐堤港店)', 1, 'https://img.meituan.net/msmerchant/232f8fdf09050838bd33fb24e79f30f9606056.jpg,https://qcloud.dpfile.com/pc/rDe48Xe15nQOHCcEEkmKUp5wEKWbimt-HDeqYRWsYJseXNncvMiXbuED7x1tXqN4uzFvxlbkWx5uwqY2qcjixFEuLYk00OmSS1IdNpm8K8sG4JN9RIm2mTKcbLtc2o2vmIU_8ZGOT1OjpJmLxG6urQ.jpg', '拱宸桥/上塘', '丽水路66号远洋乐堤港商城2期1层B115号', 120.146659, 30.312742, 290, 0000013519, 0000009529, 49, '11:00-22:00', '2021-12-22 19:17:15', '2022-01-11 16:12:51');
INSERT INTO `tb_shop` VALUES (5, '海底捞火锅(水晶城购物中心店）', 1, 'https://img.meituan.net/msmerchant/054b5de0ba0b50c18a620cc37482129a45739.jpg,https://img.meituan.net/msmerchant/59b7eff9b60908d52bd4aea9ff356e6d145920.jpg,https://qcloud.dpfile.com/pc/Qe2PTEuvtJ5skpUXKKoW9OQ20qc7nIpHYEqJGBStJx0mpoyeBPQOJE4vOdYZwm9AuzFvxlbkWx5uwqY2qcjixFEuLYk00OmSS1IdNpm8K8sG4JN9RIm2mTKcbLtc2o2vmIU_8ZGOT1OjpJmLxG6urQ.jpg', '大关', '上塘路458号水晶城购物中心F6', 120.15778, 30.310633, 104, 0000004125, 0000002764, 49, '10:00-07:00', '2021-12-22 19:20:58', '2022-01-11 16:13:01');
INSERT INTO `tb_shop` VALUES (6, '幸福里老北京涮锅（丝联店）', 1, 'https://img.meituan.net/msmerchant/e71a2d0d693b3033c15522c43e03f09198239.jpg,https://img.meituan.net/msmerchant/9f8a966d60ffba00daf35458522273ca658239.jpg,https://img.meituan.net/msmerchant/ef9ca5ef6c05d381946fe4a9aa7d9808554502.jpg', '拱宸桥/上塘', '金华南路189号丝联166号', 120.148603, 30.318618, 130, 0000009531, 0000007324, 46, '11:00-13:50,17:00-20:50', '2021-12-22 19:24:53', '2022-01-11 16:13:09');
INSERT INTO `tb_shop` VALUES (7, '炉鱼(拱墅万达广场店)', 1, 'https://img.meituan.net/msmerchant/909434939a49b36f340523232924402166854.jpg,https://img.meituan.net/msmerchant/32fd2425f12e27db0160e837461c10303700032.jpg,https://img.meituan.net/msmerchant/f7022258ccb8dabef62a0514d3129562871160.jpg', '北部新城', '杭行路666号万达商业中心4幢2单元409室(铺位号4005)', 120.124691, 30.336819, 85, 0000002631, 0000001320, 47, '00:00-24:00', '2021-12-22 19:40:52', '2022-01-11 16:13:19');
INSERT INTO `tb_shop` VALUES (8, '浅草屋寿司（运河上街店）', 1, 'https://img.meituan.net/msmerchant/cf3dff697bf7f6e11f4b79c4e7d989e4591290.jpg,https://img.meituan.net/msmerchant/0b463f545355c8d8f021eb2987dcd0c8567811.jpg,https://img.meituan.net/msmerchant/c3c2516939efaf36c4ccc64b0e629fad587907.jpg', '运河上街', '拱墅区金华路80号运河上街B1', 120.150526, 30.325231, 88, 0000002406, 0000001206, 46, ' 11:00-21:30', '2021-12-22 19:51:06', '2022-01-11 16:13:25');
INSERT INTO `tb_shop` VALUES (9, '羊老三羊蝎子牛仔排北派炭火锅(运河上街店)', 1, 'https://p0.meituan.net/biztone/163160492_1624251899456.jpeg,https://img.meituan.net/msmerchant/e478eb16f7e31a7f8b29b5e3bab6de205500837.jpg,https://img.meituan.net/msmerchant/6173eb1d18b9d70ace7fdb3f2dd939662884857.jpg', '运河上街', '台州路2号运河上街购物中心F5', 120.150598, 30.325251, 101, 0000002763, 0000001363, 44, '11:00-21:30', '2021-12-22 19:53:59', '2022-01-11 16:13:34');
INSERT INTO `tb_shop` VALUES (10, '开乐迪KTV（运河上街店）', 2, 'https://p0.meituan.net/joymerchant/a575fd4adb0b9099c5c410058148b307-674435191.jpg,https://p0.meituan.net/merchantpic/68f11bf850e25e437c5f67decfd694ab2541634.jpg,https://p0.meituan.net/dpdeal/cb3a12225860ba2875e4ea26c6d14fcc197016.jpg', '运河上街', '台州路2号运河上街购物中心F4', 120.149093, 30.324666, 67, 0000026891, 0000000902, 37, '00:00-24:00', '2021-12-22 20:25:16', '2021-12-22 20:25:16');
INSERT INTO `tb_shop` VALUES (11, 'INLOVE KTV(水晶城店)', 2, 'https://p0.meituan.net/dpmerchantpic/53e74b200211d68988a4f02ae9912c6c1076826.jpg,https://qcloud.dpfile.com/pc/4iWtIvzLzwM2MGgyPu1PCDb4SWEaKqUeHm--YAt1EwR5tn8kypBcqNwHnjg96EvT_Gd2X_f-v9T8Yj4uLt25Gg.jpg,https://qcloud.dpfile.com/pc/WZsJWRI447x1VG2x48Ujgu7vwqksi_9WitdKI4j3jvIgX4MZOpGNaFtM93oSSizbGybIjx5eX6WNgCPvcASYAw.jpg', '水晶城', '上塘路458号水晶城购物中心6层', 120.15853, 30.310002, 75, 0000035977, 0000005684, 47, '11:30-06:00', '2021-12-22 20:29:02', '2021-12-22 20:39:00');
INSERT INTO `tb_shop` VALUES (12, '魅(杭州远洋乐堤港店)', 2, 'https://p0.meituan.net/dpmerchantpic/63833f6ba0393e2e8722420ef33f3d40466664.jpg,https://p0.meituan.net/dpmerchantpic/ae3c94cc92c529c4b1d7f68cebed33fa105810.png,', '远洋乐堤港', '丽水路58号远洋乐堤港F4', 120.14983, 30.31211, 88, 0000006444, 0000000235, 46, '10:00-02:00', '2021-12-22 20:34:34', '2021-12-22 20:34:34');
INSERT INTO `tb_shop` VALUES (13, '讴K拉量贩KTV(北城天地店)', 2, 'https://p1.meituan.net/merchantpic/598c83a8c0d06fe79ca01056e214d345875600.jpg,https://qcloud.dpfile.com/pc/HhvI0YyocYHRfGwJWqPQr34hRGRl4cWdvlNwn3dqghvi4WXlM2FY1te0-7pE3Wb9_Gd2X_f-v9T8Yj4uLt25Gg.jpg,https://qcloud.dpfile.com/pc/F5ZVzZaXFE27kvQzPnaL4V8O9QCpVw2nkzGrxZE8BqXgkfyTpNExfNG5CEPQX4pjGybIjx5eX6WNgCPvcASYAw.jpg', 'D32天阳购物中心', '湖州街567号北城天地5层', 120.130453, 30.327655, 58, 0000018997, 0000001857, 41, '12:00-02:00', '2021-12-22 20:38:54', '2021-12-22 20:40:04');
INSERT INTO `tb_shop` VALUES (14, '星聚会KTV(拱墅区万达店)', 2, 'https://p0.meituan.net/dpmerchantpic/f4cd6d8d4eb1959c3ea826aa05a552c01840451.jpg,https://p0.meituan.net/dpmerchantpic/2efc07aed856a8ab0fc75c86f4b9b0061655777.jpg,https://qcloud.dpfile.com/pc/zWfzzIorCohKT0bFwsfAlHuayWjI6DBEMPHHncmz36EEMU9f48PuD9VxLLDAjdoU_Gd2X_f-v9T8Yj4uLt25Gg.jpg', '北部新城', '杭行路666号万达广场C座1-2F', 120.128958, 30.337252, 60, 0000017771, 0000000685, 47, '10:00-22:00', '2021-12-22 20:48:54', '2021-12-22 20:48:54');

-- ----------------------------
-- Table structure for tb_shop_type
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop_type`;
CREATE TABLE `tb_shop_type`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型名称',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `sort` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '顺序',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of tb_shop_type
-- ----------------------------
INSERT INTO `tb_shop_type` VALUES (1, '美食', '/types/ms.png', 1, '2021-12-22 20:17:47', '2021-12-23 11:24:31');
INSERT INTO `tb_shop_type` VALUES (2, 'KTV', '/types/KTV.png', 2, '2021-12-22 20:18:27', '2021-12-23 11:24:31');
INSERT INTO `tb_shop_type` VALUES (3, '丽人·美发', '/types/lrmf.png', 3, '2021-12-22 20:18:48', '2021-12-23 11:24:31');
INSERT INTO `tb_shop_type` VALUES (4, '健身运动', '/types/jsyd.png', 10, '2021-12-22 20:19:04', '2021-12-23 11:24:31');
INSERT INTO `tb_shop_type` VALUES (5, '按摩·足疗', '/types/amzl.png', 5, '2021-12-22 20:19:27', '2021-12-23 11:24:31');
INSERT INTO `tb_shop_type` VALUES (6, '美容SPA', '/types/spa.png', 6, '2021-12-22 20:19:35', '2021-12-23 11:24:31');
INSERT INTO `tb_shop_type` VALUES (7, '亲子游乐', '/types/qzyl.png', 7, '2021-12-22 20:19:53', '2021-12-23 11:24:31');
INSERT INTO `tb_shop_type` VALUES (8, '酒吧', '/types/jiuba.png', 8, '2021-12-22 20:20:02', '2021-12-23 11:24:31');
INSERT INTO `tb_shop_type` VALUES (9, '轰趴馆', '/types/hpg.png', 9, '2021-12-22 20:20:08', '2021-12-23 11:24:31');
INSERT INTO `tb_shop_type` VALUES (10, '美睫·美甲', '/types/mjmj.png', 4, '2021-12-22 20:21:46', '2021-12-23 11:24:31');

-- ----------------------------
-- Table structure for tb_sign
-- ----------------------------
DROP TABLE IF EXISTS `tb_sign`;
CREATE TABLE `tb_sign`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(0) UNSIGNED NOT NULL COMMENT '用户id',
  `year` year NOT NULL COMMENT '签到的年',
  `month` tinyint(0) NOT NULL COMMENT '签到的月',
  `date` date NOT NULL COMMENT '签到的日期',
  `is_backup` tinyint(0) UNSIGNED NULL DEFAULT NULL COMMENT '是否补签',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of tb_sign
-- ----------------------------

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号码',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '密码，加密存储',
  `nick_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '昵称，默认是用户id',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '人物头像',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniqe_key_phone`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1111 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, '13686869696', '', '小鱼同学', '/imgs/blogs/blog1.jpg', '2021-12-24 10:27:19', '2022-01-11 16:04:00');
INSERT INTO `tb_user` VALUES (2, '13838411438', '', '可可今天不吃肉', '/imgs/icons/kkjtbcr.jpg', '2021-12-24 15:14:39', '2021-12-28 19:58:04');
INSERT INTO `tb_user` VALUES (4, '13456789011', '', 'user_slxaxy2au9f3tanffaxr', '', '2022-01-07 12:07:53', '2022-01-07 12:07:53');
INSERT INTO `tb_user` VALUES (5, '13456789001', '', '可爱多', '/imgs/icons/user5-icon.png', '2022-01-07 16:11:33', '2022-03-11 09:09:20');
INSERT INTO `tb_user` VALUES (6, '13456762069', '', 'user_xn5wr3hpsv', '', '2022-02-07 17:54:10', '2022-02-07 17:54:10');
INSERT INTO `tb_user` VALUES (1010, '13721016950', '', 'user_7jfzlubi9h', '', '2024-04-08 20:46:53', '2024-04-08 20:46:53');
INSERT INTO `tb_user` VALUES (1011, '17763738453', '', '蔡天翊', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1012, '17648484324', '', '陈伟宸', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1013, '17749500947', '', '韩懿轩', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1014, '15002575171', '', '戴苑博', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1015, '15297368247', '', '姚峻熙', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1016, '15548291523', '', '范鹏煊', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1017, '15277486128', '', '白天翊', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1018, '17554715692', '', '杜俊驰', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1019, '15332343428', '', '丁伟诚', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1020, '17851009020', '', '廖思淼', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1021, '17006762610', '', '韦雪松', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1022, '17362601256', '', '李建辉', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1023, '15344024106', '', '潘越泽', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1024, '15281298764', '', '程凯瑞', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1025, '14570630061', '', '段子涵', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1026, '14596837511', '', '李思源', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1027, '17204191033', '', '贾炫明', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1028, '17087117958', '', '卢瑾瑜', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1029, '15944292631', '', '秦越彬', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1030, '14779595482', '', '谢鹏涛', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1031, '15273778114', '', '郑楷瑞', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1032, '15173822622', '', '冯志泽', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1033, '15319760906', '', '胡浩', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1034, '15215133918', '', '邱志泽', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1035, '17263322721', '', '叶明辉', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1036, '15930093368', '', '郭正豪', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1037, '14579086473', '', '何浩宇', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1038, '17631531831', '', '顾展鹏', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1039, '15810560700', '', '刘风华', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1040, '17037033042', '', '陶皓轩', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1041, '15776608822', '', '贾子轩', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1042, '15046165397', '', '汪睿渊', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1043, '17564694378', '', '钟立辉', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1044, '13520196399', '', '吕烨霖', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1045, '17087585076', '', '邓峻熙', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1046, '17004757001', '', '江钰轩', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1047, '15598160177', '', '谭振家', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1048, '15620545927', '', '杜建辉', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1049, '14597247208', '', '钟绍齐', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1050, '15808297938', '', '陆峻熙', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1051, '15700617316', '', '熊哲瀚', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1052, '15087020102', '', '陶立轩', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1053, '17844463938', '', '白胤祥', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1054, '15657518925', '', '郑思远', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1055, '15650085136', '', '钱绍齐', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1056, '15053098710', '', '顾烨伟', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1057, '15756543665', '', '于致远', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1058, '15337578907', '', '秦浩轩', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1059, '18183251009', '', '雷炫明', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1060, '15899120000', '', '许明', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1061, '15020663272', '', '钟皓轩', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1062, '17699407122', '', '李靖琪', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1063, '13446728501', '', '黄绍辉', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1064, '15354081130', '', '田晓啸', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1065, '14512724158', '', '薛振家', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1066, '17173539781', '', '谢雨泽', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1067, '17315994539', '', '范楷瑞', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1068, '14516998463', '', '曾鑫磊', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1069, '15093291924', '', '何弘文', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1070, '14564870387', '', '萧思远', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1071, '15562198665', '', '戴苑博', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1072, '15729669076', '', '孟琪', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1073, '17333654098', '', '曾瑾瑜', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1074, '15063550680', '', '戴子骞', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1075, '17836095199', '', '陶越彬', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1076, '17840559385', '', '冯明', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1077, '17350874762', '', '丁伟祺', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1078, '14758359204', '', '唐胤祥', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1079, '15341175384', '', '曾乐驹', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1080, '15904230151', '', '秦荣轩', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1081, '17857522362', '', '陈晋鹏', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1082, '18470303091', '', '郝靖琪', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1083, '15010693991', '', '邹立轩', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1084, '15900256371', '', '白思聪', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1085, '15533546196', '', '魏凯瑞', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1086, '15224626376', '', '侯越泽', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1087, '18700409853', '', '于伟泽', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1088, '15956344149', '', '袁浩轩', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1089, '17750782854', '', '袁昊强', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1090, '14558059154', '', '陈金鑫', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1091, '17634216653', '', '秦航', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1092, '17579917239', '', '唐雨泽', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1093, '15035544073', '', '朱越彬', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1094, '17754912056', '', '贾擎苍', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1095, '15882746943', '', '王锦程', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1096, '15543797934', '', '毛熠彤', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1097, '15760939910', '', '谭胤祥', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1098, '15557498902', '', '杜子骞', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1099, '15769552001', '', '薛绍辉', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1100, '13927060515', '', '侯雨泽', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1101, '14529696127', '', '李煜城', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1102, '15115549479', '', '吕雪松', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1103, '15032547013', '', '徐航', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1104, '17690823129', '', '董峻熙', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1105, '15182245617', '', '严昊焱', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1106, '15364461515', '', '薛苑博', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1107, '15623532484', '', '刘果', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1108, '17848412296', '', '邱浩轩', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1109, '13611978812', '', '丁昊强', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');
INSERT INTO `tb_user` VALUES (1110, '13696297815', '', '孟昊天', '', '2024-04-15 23:05:00', '2024-04-15 23:05:00');

-- ----------------------------
-- Table structure for tb_user_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_info`;
CREATE TABLE `tb_user_info`  (
  `user_id` bigint(0) UNSIGNED NOT NULL COMMENT '主键，用户id',
  `city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '城市名称',
  `introduce` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个人介绍，不要超过128个字符',
  `fans` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '粉丝数量',
  `followee` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '关注的人的数量',
  `gender` tinyint(0) UNSIGNED NULL DEFAULT 0 COMMENT '性别，0：男，1：女',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `credits` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '积分',
  `level` tinyint(0) UNSIGNED NULL DEFAULT 0 COMMENT '会员级别，0~9级,0代表未开通会员',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of tb_user_info
-- ----------------------------

-- ----------------------------
-- Table structure for tb_voucher
-- ----------------------------
DROP TABLE IF EXISTS `tb_voucher`;
CREATE TABLE `tb_voucher`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `shop_id` bigint(0) UNSIGNED NULL DEFAULT NULL COMMENT '商铺id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '代金券标题',
  `sub_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '副标题',
  `rules` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '使用规则',
  `pay_value` bigint(0) UNSIGNED NOT NULL COMMENT '支付金额，单位是分。例如200代表2元',
  `actual_value` bigint(0) NOT NULL COMMENT '抵扣金额，单位是分。例如200代表2元',
  `type` tinyint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '0,普通券；1,秒杀券',
  `status` tinyint(0) UNSIGNED NOT NULL DEFAULT 1 COMMENT '1,上架; 2,下架; 3,过期',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of tb_voucher
-- ----------------------------
INSERT INTO `tb_voucher` VALUES (1, 1, '50元代金券', '周一至周日均可使用', '全场通用\\n无需预约\\n可无限叠加\\不兑现、不找零\\n仅限堂食', 4750, 5000, 0, 1, '2022-01-04 09:42:39', '2022-01-04 09:43:31');
INSERT INTO `tb_voucher` VALUES (2, 1, '100元代金券', '周一至周日均可使用', '全场通用\\n无需预约\\n可无限叠加\\不兑现、不找零\\n仅限堂食', 5000, 10000, 1, 1, '2024-04-13 09:54:12', '2024-04-13 09:54:12');
INSERT INTO `tb_voucher` VALUES (15, 2, '100元代金券', '周一至周日均可使用', '全场通用\\n无需预约\\n可无限叠加\\不兑现、不找零\\n仅限堂食', 5000, 10000, 1, 1, '2024-04-18 15:30:36', '2024-04-18 15:30:36');

-- ----------------------------
-- Table structure for tb_voucher_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_voucher_order`;
CREATE TABLE `tb_voucher_order`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `user_id` bigint(0) UNSIGNED NOT NULL COMMENT '下单的用户id',
  `voucher_id` bigint(0) UNSIGNED NOT NULL COMMENT '购买的代金券id',
  `pay_type` tinyint(0) UNSIGNED NOT NULL DEFAULT 1 COMMENT '支付方式 1：余额支付；2：支付宝；3：微信',
  `status` tinyint(0) UNSIGNED NOT NULL DEFAULT 1 COMMENT '订单状态，1：未支付；2：已支付；3：已核销；4：已取消；5：退款中；6：已退款',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '下单时间',
  `pay_time` timestamp(0) NULL DEFAULT NULL COMMENT '支付时间',
  `use_time` timestamp(0) NULL DEFAULT NULL COMMENT '核销时间',
  `refund_time` timestamp(0) NULL DEFAULT NULL COMMENT '退款时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of tb_voucher_order
-- ----------------------------
INSERT INTO `tb_voucher_order` VALUES (310493171626803201, 1063, 2, 1, 1, '2024-04-16 17:12:05', NULL, NULL, NULL, '2024-04-16 17:12:05');
INSERT INTO `tb_voucher_order` VALUES (310493171626803202, 6, 2, 1, 1, '2024-04-16 17:12:05', NULL, NULL, NULL, '2024-04-16 17:12:05');
INSERT INTO `tb_voucher_order` VALUES (310493171626803203, 5, 2, 1, 1, '2024-04-16 17:12:05', NULL, NULL, NULL, '2024-04-16 17:12:05');
INSERT INTO `tb_voucher_order` VALUES (310493171626803204, 4, 2, 1, 1, '2024-04-16 17:12:05', NULL, NULL, NULL, '2024-04-16 17:12:05');
INSERT INTO `tb_voucher_order` VALUES (310493171626803205, 1044, 2, 1, 1, '2024-04-16 17:12:05', NULL, NULL, NULL, '2024-04-16 17:12:05');
INSERT INTO `tb_voucher_order` VALUES (310493171626803206, 1109, 2, 1, 1, '2024-04-16 17:12:05', NULL, NULL, NULL, '2024-04-16 17:12:05');
INSERT INTO `tb_voucher_order` VALUES (310493171626803207, 1, 2, 1, 1, '2024-04-16 17:12:05', NULL, NULL, NULL, '2024-04-16 17:12:05');
INSERT INTO `tb_voucher_order` VALUES (310493171626803208, 1110, 2, 1, 1, '2024-04-16 17:12:05', NULL, NULL, NULL, '2024-04-16 17:12:05');
INSERT INTO `tb_voucher_order` VALUES (310493171626803209, 1010, 2, 1, 1, '2024-04-16 17:12:05', NULL, NULL, NULL, '2024-04-16 17:12:05');
INSERT INTO `tb_voucher_order` VALUES (310493171626803210, 2, 2, 1, 1, '2024-04-16 17:12:05', NULL, NULL, NULL, '2024-04-16 17:12:05');
INSERT INTO `tb_voucher_order` VALUES (311211088295232498, 1063, 15, 1, 1, '2024-04-18 15:37:58', NULL, NULL, NULL, '2024-04-18 15:37:58');
INSERT INTO `tb_voucher_order` VALUES (311211088295232499, 6, 15, 1, 1, '2024-04-18 15:37:58', NULL, NULL, NULL, '2024-04-18 15:37:58');
INSERT INTO `tb_voucher_order` VALUES (311211088295232500, 5, 15, 1, 1, '2024-04-18 15:37:58', NULL, NULL, NULL, '2024-04-18 15:37:58');
INSERT INTO `tb_voucher_order` VALUES (311211088295232501, 4, 15, 1, 1, '2024-04-18 15:37:58', NULL, NULL, NULL, '2024-04-18 15:37:58');
INSERT INTO `tb_voucher_order` VALUES (311211088295232502, 1044, 15, 1, 1, '2024-04-18 15:37:58', NULL, NULL, NULL, '2024-04-18 15:37:58');
INSERT INTO `tb_voucher_order` VALUES (311211088295232503, 1109, 15, 1, 1, '2024-04-18 15:37:58', NULL, NULL, NULL, '2024-04-18 15:37:58');
INSERT INTO `tb_voucher_order` VALUES (311211088295232504, 1, 15, 1, 1, '2024-04-18 15:37:58', NULL, NULL, NULL, '2024-04-18 15:37:58');
INSERT INTO `tb_voucher_order` VALUES (311211088295232505, 1110, 15, 1, 1, '2024-04-18 15:37:58', NULL, NULL, NULL, '2024-04-18 15:37:58');
INSERT INTO `tb_voucher_order` VALUES (311211088295232506, 1010, 15, 1, 1, '2024-04-18 15:37:58', NULL, NULL, NULL, '2024-04-18 15:37:58');
INSERT INTO `tb_voucher_order` VALUES (311211088295232507, 2, 15, 1, 1, '2024-04-18 15:37:58', NULL, NULL, NULL, '2024-04-18 15:37:58');

SET FOREIGN_KEY_CHECKS = 1;
