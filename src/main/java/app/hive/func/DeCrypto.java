package app.hive.func;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static utils.RSAUtils.*;

/**
 * 自定义 UDF 函数，需要继承 GenericUDF 类
 * 需求: Crypto方式解密PII数字
 */
public class DeCrypto extends GenericUDF {

    /** *
     * @param arguments 输入参数类型的鉴别器对象 * @return 返回值类型的鉴别器对象
     * @throws UDFArgumentException
     */
    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        // 判断输入参数的个数
        if(arguments.length != 1){
            throw new UDFArgumentLengthException("Input Args LengthError! Please input one arg");
        }
        return PrimitiveObjectInspectorFactory.javaStringObjectInspector;
    }

    /**
     * 函数的逻辑处理
     * @param arguments 输入的参数 * @return 返回值
     * @throws HiveException
     */
    @Override
    public String evaluate(DeferredObject[] arguments) throws HiveException {

        if(arguments[0].get() == null){
            return null;
        }
        //引入私钥
        String testPrivateKeyFilePath = "rsa.key";
        String testPrivateKey = readFile(testPrivateKeyFilePath);

        //私钥解密
        String enCodeData = arguments[0].get().toString(); // 获取加密数据
        try {
            String decodedData = privateEncrypt(enCodeData, getPrivateKey(testPrivateKey)); // 获取解密数据
//            return Double.parseDouble(decodedData);
            return decodedData;

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return testPrivateKey;
    }

    @Override
    public String getDisplayString(String[] strings) {
        return null;
    }
}
