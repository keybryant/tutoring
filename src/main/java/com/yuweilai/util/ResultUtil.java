package com.yuweilai.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 87734 on 2017/11/27.
 */
public class ResultUtil implements Serializable {

        // 定义jackson对象
        private static final ObjectMapper MAPPER = new ObjectMapper();

        // 响应业务状态
        private Integer status;

        // 响应消息
        private String msg;

        // 响应中的数据
        private Object data;

        public static ResultUtil build(Integer status, String msg, Object data) {
            return new ResultUtil(status, msg, data);
        }

        public static ResultUtil ok(Object data) {
            return new ResultUtil(data);
        }

        public static ResultUtil ok() {
            return new ResultUtil(null);
        }

        public ResultUtil() {

        }

        public static ResultUtil build(Integer status, String msg) {
            return new ResultUtil(status, msg, null);
        }

        public ResultUtil(Integer status, String msg, Object data) {
            this.status = status;
            this.msg = msg;
            this.data = data;
        }

        public ResultUtil(Object data) {
            this.status = 200;
            this.msg = "OK";
            this.data = data;
        }

//    public Boolean isOK() {
//        return this.status == 200;
//    }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        /**
         * 将json结果集转化为TaotaoResult对象
         *
         * @param jsonData json数据
         * @param clazz TaotaoResult中的object类型
         * @return
         */
        public static ResultUtil formatToPojo(String jsonData, Class<?> clazz) {
            try {
                if (clazz == null) {
                    return MAPPER.readValue(jsonData, ResultUtil.class);
                }
                JsonNode jsonNode = MAPPER.readTree(jsonData);
                JsonNode data = jsonNode.get("data");
                Object obj = null;
                if (clazz != null) {
                    if (data.isObject()) {
                        obj = MAPPER.readValue(data.traverse(), clazz);
                    } else if (data.isTextual()) {
                        obj = MAPPER.readValue(data.asText(), clazz);
                    }
                }
                return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
            } catch (Exception e) {
                return null;
            }
        }

        /**
         * 没有object对象的转化
         *
         * @param json
         * @return
         */
        public static ResultUtil format(String json) {
            try {
                return MAPPER.readValue(json, ResultUtil.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * Object是集合转化
         *
         * @param jsonData json数据
         * @param clazz 集合中的类型
         * @return
         */
        public static ResultUtil formatToList(String jsonData, Class<?> clazz) {
            try {
                JsonNode jsonNode = MAPPER.readTree(jsonData);
                JsonNode data = jsonNode.get("data");
                Object obj = null;
                if (data.isArray() && data.size() > 0) {
                    obj = MAPPER.readValue(data.traverse(),
                            MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
                }
                return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
            } catch (Exception e) {
                return null;
            }
        }

    }
