package com.mall.common.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.mall.common.handler.GlobalException;
import com.mall.common.handler.GlobalExceptionEnum;
import com.mall.sys.entity.MallSysUser;
import com.mall.sys.mapper.MallSysUserMapper;

import java.util.HashMap;
import java.util.Map;


public class MD5Util {
    static Digester md5 = new Digester(DigestAlgorithm.MD5);

    private static MallSysUserMapper mallSysUserMapper = Db.getMapper(MallSysUserMapper.class);

    /**
     * 密码加密
     *
     * @param pass
     * @return
     */
    public static Map<String, String> encryptionPass(String pass) {
        Map<String, String> map = new HashMap<>();
        String pss = md5.digestHex(pass);
        String a = SnowflakeUtil.randomLong() + "";
        String salt = md5.digestHex(a);
        String password = md5.digestHex(pss + salt);
        map.put("password", password);
        map.put("salt", salt);
        return map;
    }

    /**
     验证密码
     cipherText :数据库存储密文
     salt       :数据库储备盐
     userPass   :用户密码
     */
    public static void verification(String cipherText, String salt, String userPass, Long userId) {
        Integer parameterMap = 3;
        userPass = md5.digestHex(userPass);
        String s = md5.digestHex(userPass + salt);
        boolean equals = cipherText.equals(s);
        if (!equals) {
            Object loginErrorFrequency = CacheTemplateUtil.get("login_error_frequency_" + userId);
            if (loginErrorFrequency == null) {
                CacheTemplateUtil.set("login_error_frequency_" + userId, 1 + "");
                throw new GlobalException(GlobalExceptionEnum.LOGIN_FAILED.getCode(), "密码错误你还有" + (parameterMap) + "次机会");
            } else {
                Integer lo = (Integer) loginErrorFrequency;
                CacheTemplateUtil.set("login_error_frequency_" + userId, lo + 1 + "");
                if (parameterMap - lo <= 0) {
                    CacheTemplateUtil.set("locking_data_" + userId, 1 + "");
                    MallSysUser mallSysUser = new MallSysUser();
                    mallSysUser.setId(userId);
                    mallSysUser.setMgState(false);
                    mallSysUserMapper.updateById(mallSysUser);

                    throw new GlobalException(GlobalExceptionEnum.LOGIN_FAILED.getCode(), "密码错误次数已达到上限请联系管理员");
                }
                throw new GlobalException(GlobalExceptionEnum.LOGIN_FAILED.getCode(), "密码错误你还有" + (parameterMap - lo) + "次机会");
            }

//            throw new GlobalException(GlobalExceptionEnum.LOGIN_FAILED.getCode(),"密码错误");
        }
    }


}
