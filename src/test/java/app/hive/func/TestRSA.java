package app.hive.func;

import lombok.SneakyThrows;
import utils.RSAUtils;

import javax.crypto.Cipher;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static utils.RSAUtils.getPrivateKey;
import static utils.RSAUtils.privateDecrypt;


public class TestRSA {
    public static void main (String[] args) throws Exception {
        Map<String, String> keyMap = RSAUtils.createKeys(1024);
        String  publicKey = keyMap.get("publicKey");
        String  privateKey = keyMap.get("privateKey");
        System.out.println("公钥: \n\r" + publicKey);
        System.out.println("私钥： \n\r" + privateKey);

        System.out.println("公钥加密——私钥解密");
        String str = "站在大明门前守卫的禁卫军，事先没有接到\n" +
                "有关的命令，但看到大批盛装的官员来临，也就\n" +
                "以为确系举行大典，因而未加询问。进大明门即\n" +
                "为皇城。文武百官看到端门午门之前气氛平静，\n" +
                "城楼上下也无朝会的迹象，既无几案，站队点名\n" +
                "的御史和御前侍卫“大汉将军”也不见踪影，不免\n" +
                "心中揣测，互相询问：所谓午朝是否讹传？";
        System.out.println("\r明文：\r\n" + str);
        System.out.println("\r明文大小：\r\n" + str.getBytes().length);
        //公钥加密
        String encodedData = RSAUtils.publicEncrypt(str, RSAUtils.getPublicKey(publicKey));

        System.out.println("密文：\r\n" + encodedData);
        //私钥解密
        String decodedData = privateDecrypt(encodedData, getPrivateKey(privateKey));
        System.out.println("解密后文字: \r\n" + decodedData);

        System.out.println("__________________TEST__________________");


        String testPrivateKeyFilePath = "/Users/diy006/IdeaProjects/CustomerFuncs/src/test/resources/rsa.key";
        String testPrivateKey = readFile(testPrivateKeyFilePath);

        String encodeDecodeFile = "/Users/diy006/IdeaProjects/CustomerFuncs/src/test/resources/encodeDecode.csv";
        HashMap<String, Double> encodeDecodeMap = readFileToMap(encodeDecodeFile);


        for(String testEncodedData : encodeDecodeMap.keySet()){
            //私钥解密
            String testDecodedData = privateDecrypt(testEncodedData, getPrivateKey(testPrivateKey));
            System.out.println(
                    "原始数值: " + encodeDecodeMap.get(testEncodedData) +"\t"+
                    "解密后数值: " + testDecodedData +"\t"+
                    "差值: " + Math.abs(encodeDecodeMap.get(testEncodedData) - Double.parseDouble(testDecodedData)));
        }
    }

    @SneakyThrows
    private static HashMap<String, Double> readFileToMap(String encodeDecodeFile) {
        HashMap<String, Double> encodeDecodeMap = new HashMap<>();
        //BufferedReader是可以按行读取文件
        FileInputStream inputStream = new FileInputStream(encodeDecodeFile);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line = null;
        while((line = bufferedReader.readLine()) != null)
        {
            String encodeData = line.split("    ")[0];
            String decodeData = line.split("    ")[1];
            encodeDecodeMap.put(encodeData,Double.parseDouble(decodeData));
        }
        //close
        inputStream.close();
        bufferedReader.close();
        return encodeDecodeMap;
    }

    private static String readFile(String filePath) {
        File files = new File(filePath);
        FileInputStream is = null;
        StringBuilder stringBuilder = null;
        try {
            if (files.length() != 0) {
                /**
                 * 文件有内容才去读文件
                 */
                is = new FileInputStream(files);
                InputStreamReader streamReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(streamReader);
                String line;
                stringBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    // stringBuilder.append(line);
                    stringBuilder.append(line);
                }
                reader.close();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(stringBuilder);
    }

}
