package cn.zb.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;

import cn.zb.utils.FileUtils;
import cn.zb.utils.Time;

public class FileBufferTest {

	public static void main(String[] args) throws Exception {
		String in = "d://1//Desktop.zip";

		String out = "d://1//111.zip";
		
		
		FileInputStream fin = new FileInputStream(in);

		FileOutputStream fos = new FileOutputStream(out);
		
		Time t=new Time();
		FileChannel outChannal = fos.getChannel();
		FileChannel channal = fin.getChannel();

		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		while (true) {
			buffer.clear();

			int r = channal.read(buffer);
			if (r == -1) {
				break;
			}
			buffer.flip();
			outChannal.write(buffer);

		}
		//FileUtils.outFile(new File(in), fos);
		t.stop();
		System.out.println(t.getTime());

	}

}
