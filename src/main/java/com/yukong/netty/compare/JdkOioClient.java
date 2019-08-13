package com.yukong.netty.compare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author: yukong
 * @date: 2018/12/18 16:43
 */
public class JdkOioClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1", 6789));
        System.out.println("connection server");
        System.out.println("Hello");
        // 签约
        //String message = "1312e60935edda9f5e4075a6c43d46c4003c954ffcdb8de87d745142b2024210f9d36a3e7141b3062accf5eb53b4d37b0d6a4c31923ed665c81386da966175439a668aa1afd83fd06df6fc839f7cc2f4af8070e757ba985ae7a848e88fce5eda7285851900746cb903717f551ca4f1e5e40dfd9c3e57814b94e92a1dd6a1c400d10d8a57493c1c0b3708a788b1b7b4fb4ee068f1472f476a58c6e438ea17830d37f2d5eda862db039f7c2fd8f6a35da7fe80646632f75cf4b7d448e88fce5eda7285cdea4b98d8ba48c5594957d145a3b6c5bf514ff59fc5d3ff4ea020ec838c4d86fdb35968f8085bce16dce1943a7830c09014960d877239149fa154820a3635e461bf6a8f99dee31a35fe60f38f7b74b11eb37e5c4e95ecbb872e3a6adf440219d745ae1d98f4902db73850a76e267700a5596ccc3211ee878e62fd277cafb21316a85c7b792598c9085b149e21869f8a85f80d95f2eb8c64ff061dbdcf83613e823b053c403097cb2eb62eaa4bbbc0cf85f80d95f2eb8c64381efda4275574bb59109c5701aec5b6a2404e332e334be0d383220a90706d4f48e88fce5eda728597485ddf5945902863ab15e909d3e0a01988779bb33c0138df519dee7c0c91d1be41a8a1ee6646e0be6943392716db0941c0d3fe0f89bd33328415c082573139fd1a34660990cd7f43037908229719cef749a26bc683c562476b7763923043dc6b35dd5a06650c741a38d4bfcbab17e78baeadf22256347391f8191443f3397b436f0a0310d21628541038f2feed2d120ebd3c66fb2fd6d9c090451dc28307e33a88541d3f2dda7ed70d9884b4ac87ab30471609c6085c2f2561f755fb427c779014960d877239147a7cbda2371909298c8450ee72a48262689611a17757b36a34055ea3847d65e2aecf6d6fc2165a86";

        // 解约
        String message = "1312e60935edda9f5e4075a6c43d46c4003c954ffcdb8de87d745142b2024210f9d36a3e7141b3062accf5eb53b4d37b0d6a4c31923ed665c81386da966175439a668aa1afd83fd06df6fc839f7cc2f4af8070e757ba985ae7a848e88fce5eda7285851900746cb9037192b6d7fb53629256fd9c3e57814b94e92a1dd6a1c400d10d8a57493c1c0b3708a788b1b7b4fb4ee068f1472f476a58c6e438ea17830d37f2d5eda862db039f7c2fd8f6a35da7fe80646632f75cf4b7d448e88fce5eda7285cdea4b98d8ba48c5594957d145a3b6c5bf514ff59fc5d3ff4ea020ec838c4d86fdb35968f8085bce16dce1943a7830c09014960d877239149fa154820a3635e461bf6a8f99dee31a35fe60f38f7b74b11eb37e5c4e95ecbb872e3a6adf440219d745ae1d98f4902db73850a76e267700a5596ccc3211ee878e62fd277cafb21316a85c7b792598c9085b149e21869f8a85f80d95f2eb8c64ff061dbdcf83613e823b053c403097cb2eb62eaa4bbbc0cf85f80d95f2eb8c64381efda4275574bb59109c5701aec5b6a2404e332e334be0d383220a90706d4f48e88fce5eda728597485ddf5945902863ab15e909d3e0a01988779bb33c0138df519dee7c0c91d1be41a8a1ee6646e0be6943392716db0941c0d3fe0f89bd33328415c082573139fd1a34660990cd7f43037908229719cef749a26bc683c562476b7763923043dc6b35dd5a06650c741a38d4bfcbab17e78baeadf22256347391f8191443f3397b436f0a0310d21628541038f2feed2d120ebd3c66fb2fd6d9c090451dc28307e33a88541d3f2dda7ed70d9884b4ac87ab30471609c6085c2f2561f755fb427c779014960d877239147a7cbda2371909298c8450ee72a48262689611a17757b36aaec8049f5ff115053be9b695f4aa987c";
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(message.getBytes());
        outputStream.flush();
    }

}
