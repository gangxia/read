package com.zhufuyisheng.util;

import android.os.Environment;

public class Mstring {
	/**
	 * 关键字数据
	 */
	public static String[] keyword = { "金鳞岂是池中物", "大丑风流记", "淫荡少妇之白洁", "少年阿宾",
			"雪白的屁股", "少年大宝风流", "更年期的黄蓉", "黄蓉新传", "射雕情色篇", "射雕淫女传", "神雕、倚天",
			"少女之心", "御心香帅", "都市品香录", "女教师", "乡野痞医", "侠女虐奸史", "神雕风云", "都市奇缘",
			"极度后宫", "逃脱", "穿越之玩遍娱乐圈", "娇淫青春之放纵", "都市花缘梦(极度淫荡)", "成人小说精心收集",
			"欲望红杏", "明星潜规则之皇", "大唐风流记(艳说大唐)", "春花秋月", "乱伦系列", "离婚的女人(1-5)",
			"乱世沉沦(全)[肥水系列]", "Ｔ大校花沉沦记", "粗大的完美曲线", "大学生女友", "娇妻物语", "娇艳江湖",
			"警花少妇白艳妮", "绣榻野史", "姐夫出差了我和姐姐搞", "制服下的诱惑", "姐夫搞小姨子系列", "大侠魂山寨版",
			"风流传奇", "神雕游侠", "风流花少", "玉麟传奇", "倚天屠龙记成人版", "风流女儿国1", "风流女儿国2",
			"金瓶梅", "《成人笑话》582则", "女性怎样才能获得性高潮", "穿越淫荡公主", "三宝局长", "乱伦性事",
			"淫欲的学园", "欲火焚身", "穿越之淫荡掌门", "有种床上单挑", "爹爹好狂野", "甜蜜的婚姻生活", "巨蟒少年",
			"天生媚骨", "陈皮皮的斗争", "民间淫皇艳后宫", "神雕短篇集", "神雕乱伦篇", "阿兵哥艳遇录", "阿里布达年代记",
			"爱神之传奇", "不良少女日记", "沧澜曲", "东北风情之宿命熟女之惑", "风尘劫", "风流侍女", "风月大陆",
			"夫妇乐园扎记", "海盗的悠闲生活", "黑天使", "黑星女侠", "红楼梦之绮梦仙缘", "混蛋神风流史", "江湖淫娘",
			"狡猾的风水相师", "狂风暴雨", "俪影蝎心", "书剑恩仇录之骆冰淫传", "美少妇的哀羞", "梦中的女孩",
			"秦青的性福生活", "舌战法庭", "神雕外传之郭襄", "十景缎", "睡着的武神", "四海龙女", "逃亡艳旅",
			"王子淫传", "往事追忆录", "我的性启蒙老师", "现代艳帝传奇", "星光伴我淫", "遥想当年春衫薄", "淫术炼金士" };

	/**
	 * 解密密钥
	 * 
	 * @return
	 */

	public static String decrypetkey() {
		String aeskey = "";
		try {
			aeskey = AesEncryptor
					.decrypt("zhufuyisheng",
							"AB9EBC2A119E529E7C53961E2E75F2F2C5FFAE69A352E0EB0ECEAC6A5A086601");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aeskey;
	}

	/**
	 * 域名和ip
	 * 
	 * @return
	 */
	public static String site = "http://zhufuyisheng.com/";
	/**
	 * 搜索数据
	 * 
	 * @return
	 */
	public static String search = site + "phone/read/search.php";
	/**
	 * 搜索数据数量
	 * 
	 * @return
	 */
	public static String search_page = site + "phone/read/search_page.php";
	/**
	 * 所有文件排序
	 * 
	 * @return
	 */
	public static String lab1 = site + "phone/read/lab1.php";
	/**
	 * 文件类型排序
	 * 
	 * @return
	 */
	public static String lab2 = site + "phone/read/lab2.php";
	/**
	 * 插入下载
	 * 
	 * @return
	 */
	public static String tag1 = site + "phone/read/tag1.php";
	/**
	 * 插入好评
	 * 
	 * @return
	 */
	public static String tag2 = site + "phone/read/tag2.php";
	/**
	 * 插入错误
	 * 
	 * @return
	 */
	public static String tag3 = site + "phone/read/tag3.php";
	/**
	 * 插入错误
	 * 
	 * @return
	 */
	public static String zhufu = site + "phone/zhufu/apk/zhufu.apk";
	// http://zhufuyisheng.com/phone/zhufu/apk/
	/**
	 * 文件保存目录
	 */
	public static String fileDir = Environment.getExternalStorageDirectory()
			.getPath() + "/.zhufu_read/";
	/**
	 * 提示数组
	 */
	// public static String[] msg = { "\n 书架图书长按可以好评，报错，导出apk！",
	// "\n 下载后，图书长按导出txt，可以在任何小说阅读器打开！", "\n 有问题建议记得反馈哦！",
	// "\n 我们维护软件是需要成本的，希望您多点击几个广告，举手之劳，不胜感谢！",
	// "\n  我们一直在改善软件，如果遇到BUG，希能包容！", "\n  图书导出位置为sdcard根目录txtfile文件夹！",
	// "祝福一生官网：http:www.zhufuyisheng.com" };
	public static String[] msg = { "\n 书架图书长按可以好评，报错，导出apk！",
			"\n 下载后，图书长按导出txt，可以在任何小说阅读器打开！", "\n 有问题建议记得反馈哦！",
			"\n 我们维护软件是需要成本的，希望您多点击几个广告，举手之劳，不胜感谢！",
			"\n  我们一直在改善软件，如果遇到BUG，希能包容！", "\n  图书导出位置为sdcard根目录txtfile文件夹！" };

	public static String Get_Msg() {

		int index = (int) (Math.random() * msg.length);
		return msg[index];
	}

}
