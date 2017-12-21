//package com.yuweilai;
//
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.IOException;
//import java.util.Base64;
//
///**
// * Created by 87734 on 2017/11/23.
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class Test {
//    @org.junit.Test
//    public  void main(){
//        String str="<?xml version=\"1.0\" encoding=\"GBK\"?>\n" +
//                "<conditions>\n" +
//                "\t<condition queryType=\"10100\">\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>cityid</name>\n" +
//                "\t\t\t<value>9</value>\n" +
//                "\t\t</item>\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>unitName</name>\n" +
//                "\t\t\t<value>个人业务</value>\n" +
//                "\t\t</item>\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>organUnitName</name>\n" +
//                "\t\t\t<value>佳德鑫服</value>\n" +
//                "\t\t</item>\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>userName</name>\n" +
//                "\t\t\t<value>帅越</value>\n" +
//                "\t\t</item>\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>CityId</name>\n" +
//                "\t\t\t<value>9</value>\n" +
//                "\t\t</item>\t\t\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>judgeFor</name>\n" +
//                "\t\t\t<value>1</value>\n" +
//                "\t\t</item>\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>projectName</name>\n" +
//                "\t\t\t<value>TCL翠园</value>\n" +
//                "\t\t</item>\t\t\n" +
//                "\t\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>buildingName</name>\n" +
//                "\t\t\t<value>F栋</value>\n" +
//                "\t\t</item>\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>houseName</name>\n" +
//                "\t\t\t<value>6层D房</value>\n" +
//                "\t\t</item>\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>buildingArea</name>\t\t\t\n" +
//                "\t\t\t<value>112.10</value>\n" +
//                "\t\t</item>\t\t\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>saleDate</name>\t\t\t\n" +
//                "\t\t\t<value>20151001</value>\n" +
//                "\t\t</item>\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>salePrice</name>\t\t\t\n" +
//                "\t\t\t<value>90.3456</value>\n" +
//                "\t\t</item>\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>pgsqID</name>\t\t\t\n" +
//                "\t\t\t<value>123456</value>\n" +
//                "\t\t</item>\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>pggsmc</name>\n" +
//                "\t\t\t<value>同致诚惠州分公司</value>\n" +
//                "\t\t</item>\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>curFloor</name>\n" +
//                "\t\t\t<value>6</value>\n" +
//                "\t\t</item>\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>totalFloor</name>\n" +
//                "\t\t\t<value>18</value>\n" +
//                "\t\t</item>\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>towards</name>\n" +
//                "\t\t\t<value>南</value>\n" +
//                "\t\t</item>\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>huxing</name>\n" +
//                "\t\t\t<value>三房二厅二卫一阳台</value>\n" +
//                "\t\t</item>\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>structure</name>\n" +
//                "\t\t\t<value>0</value>\n" +
//                "\t\t</item>\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>ownerType</name>\n" +
//                "\t\t\t<value>个人</value>\n" +
//                "\t\t</item>\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>houseType</name>\n" +
//                "\t\t\t<value>0</value>\n" +
//                "\t\t</item>\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>lift</name>\n" +
//                "\t\t\t<value>0</value>\n" +
//                "\t\t</item>\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>improvements</name>\n" +
//                "\t\t\t<value>精装修</value>\n" +
//                "\t\t</item>\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>address</name>\n" +
//                "\t\t\t<value>TCL翠园F栋6层D房</value>\n" +
//                "\t\t</item>\n" +
//                "\t\t<item>\n" +
//                "\t\t\t<name>content</name>\n" +
//                "\t\t\t<value>无</value>\n" +
//                "\t\t</item>\n" +
//                "\t</condition>\n" +
//                "</conditions>\n";
//        byte[] bytes=decode(str);
//        System.out.println(bytes);
//    }
//    public static byte[] decode(String str){
//        byte[] bt = null;
//        try {
//            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
//            bt = decoder.decodeBuffer( str );
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return bt;
//    }
//}
