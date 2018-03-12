package ap.com.androidmanager;

/**
 * 类描述：
 * 创建人：swallow.li
 * 创建时间： 2017/7/4.
 * Email: swallow.li@kemai.cn
 * 修改备注：
 */
public class ValidationUtils {

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    var reg = /^[1][385764][0-9]{9}$/;
    */
        String telRegex = "[1][385764]\\\\d{9}";//"[1][385764]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
//        if (StringUtils.isEmpty(mobiles)) {
//            return false;
//        }else {
//            return mobiles.matches(telRegex);
//        }
        char[] chas = mobiles.toCharArray();
        if (!"1".equals(chas[0] + "")) {
            return false;
        } else {
            // 1290
            if ("1".equals(chas[1] + "") ||
                    "2".equals(chas[1] + "") ||
                    "9".equals(chas[1] + "") ||
                    "0".equals(chas[1] + "")
                    ) {
                return false;
            }
        }
        return true;
    }
}
