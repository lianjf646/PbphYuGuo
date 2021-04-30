package com.pbph.yuguo.response;

import com.pbph.yuguo.base.BaseResponse;

/**
 * Created by 连嘉凡 on 2018/9/10.
 */

public class GetOssTokenResponse extends BaseResponse {


    /**
     * data :
     * {"securityToken":"CAIS8QF1q6Ft5B2yfSjIr4vMP47mqrwZ9bWOZ0z1hzYPePVdobf7mjz2IHtNeXNtAu4Zs/k
     * /mm1T6v8blqB6T55OSAmcNZIoLTiXJLflMeT7oMWQweEuqv/MQBq+aXPS2MvVfJ
     * +KLrf0ceusbFbpjzJ6xaCAGxypQ12iN+/i6/clFKN1ODO1dj1bHtxbCxJ
     * /ocsBTxvrOO2qLwThjxi7biMqmHIl2T8guf/km53Gs0SO0QSm8IJP+dSteKrDRtJ3IZJyX
     * +2y2OFLbafb2EZSkUMRrfos0fcapmqa7o/AWAUIug/nIuXO+9h1Kwt0dgKZxkWvVzSXGoABlTr4
     * +n5VUX4M437wVatcASfROynaVYnxf/Q1YvDF29dshtHMh6ayh39asOmEFtCjjZ61GvL6rRSxYE2zOT
     * +9EOIlYmQ6TnWvn0FF0nZXE3KsbzTJ6aot/73Uguiyh/VwUZBYCnjVpkQ0He2eINY8YIckE0IMa5MzSRXu56m0hXA
     * =","accessKeyId":"STS.NHyt4RGc8BweejDc6ZtzqNrPx",
     * "accessKeySecret":"9FG48S1JqrDafDpUkLbNJ4szWUxPNkjHCQTBMvfh2dqx",
     * "expiration":"2018-09-11T08:05:40Z","ossUrl":"http://image.pbdsh.com",
     * "ossBucketName":"ygshopbucket","ossFoldName":"ygonlinefold"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * securityToken : CAIS8QF1q6Ft5B2yfSjIr4vMP47mqrwZ9bWOZ0z1hzYPePVdobf7mjz2IHtNeXNtAu4Zs
         * /k/mm1T6v8blqB6T55OSAmcNZIoLTiXJLflMeT7oMWQweEuqv/MQBq+aXPS2MvVfJ
         * +KLrf0ceusbFbpjzJ6xaCAGxypQ12iN+/i6/clFKN1ODO1dj1bHtxbCxJ
         * /ocsBTxvrOO2qLwThjxi7biMqmHIl2T8guf/km53Gs0SO0QSm8IJP+dSteKrDRtJ3IZJyX
         * +2y2OFLbafb2EZSkUMRrfos0fcapmqa7o/AWAUIug/nIuXO+9h1Kwt0dgKZxkWvVzSXGoABlTr4
         * +n5VUX4M437wVatcASfROynaVYnxf/Q1YvDF29dshtHMh6ayh39asOmEFtCjjZ61GvL6rRSxYE2zOT
         * +9EOIlYmQ6TnWvn0FF0nZXE3KsbzTJ6aot/73Uguiyh
         * /VwUZBYCnjVpkQ0He2eINY8YIckE0IMa5MzSRXu56m0hXA=
         * accessKeyId : STS.NHyt4RGc8BweejDc6ZtzqNrPx
         * accessKeySecret : 9FG48S1JqrDafDpUkLbNJ4szWUxPNkjHCQTBMvfh2dqx
         * expiration : 2018-09-11T08:05:40Z
         * ossUrl : http://image.pbdsh.com
         * ossBucketName : ygshopbucket
         * ossFoldName : ygonlinefold
         */

        private String securityToken;
        private String accessKeyId;
        private String accessKeySecret;
        private String expiration;
        private String ossUrl;
        private String ossBucketName;
        private String ossFoldName;

        public String getSecurityToken() {
            return securityToken;
        }

        public void setSecurityToken(String securityToken) {
            this.securityToken = securityToken;
        }

        public String getAccessKeyId() {
            return accessKeyId;
        }

        public void setAccessKeyId(String accessKeyId) {
            this.accessKeyId = accessKeyId;
        }

        public String getAccessKeySecret() {
            return accessKeySecret;
        }

        public void setAccessKeySecret(String accessKeySecret) {
            this.accessKeySecret = accessKeySecret;
        }

        public String getExpiration() {
            return expiration;
        }

        public void setExpiration(String expiration) {
            this.expiration = expiration;
        }

        public String getOssUrl() {
            return ossUrl;
        }

        public void setOssUrl(String ossUrl) {
            this.ossUrl = ossUrl;
        }

        public String getOssBucketName() {
            return ossBucketName;
        }

        public void setOssBucketName(String ossBucketName) {
            this.ossBucketName = ossBucketName;
        }

        public String getOssFoldName() {
            return ossFoldName;
        }

        public void setOssFoldName(String ossFoldName) {
            this.ossFoldName = ossFoldName;
        }
    }
}
