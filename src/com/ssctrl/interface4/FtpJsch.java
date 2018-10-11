package com.ssctrl.interface4;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.UUID;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class FtpJsch {

    private static ChannelSftp sftp = null;

    //账号
    private static String user = "root";
    //主机ip
    private static String host =  "39.108.62.244";
    //密码
    private static String password = "Swy2018123";
    //端口
    private static int port = 22;
    //上传地址
    private static String directory = "/img/imgheadpic";
    //下载目录
    private static String saveFile = "D:\\VMware\\XuNiJi\\imgs";

    public static FtpJsch getConnect(){
        FtpJsch ftp = new FtpJsch();
        try {
            JSch jsch = new JSch();

            //获取sshSession  账号-ip-端口
            Session sshSession =jsch.getSession(user, host,port);
            //添加密码
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            //严格主机密钥检查
            sshConfig.put("StrictHostKeyChecking", "no");

            sshSession.setConfig(sshConfig);
            //开启sshSession链接
            sshSession.connect();
            //获取sftp通道
            Channel channel = sshSession.openChannel("sftp");
            //开启
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ftp;
    }

    /**
     *
     * @param uploadFile 上传文件的路径
     * @return 服务器上文件名
     */
    public static String upload(String uploadFile,String fileName) {
        getConnect();
        File file = null;
        try {
            sftp.cd(directory);
            file = new File(uploadFile);
            //获取随机文件名
//            fileName  = UUID.randomUUID().toString() + file.getName().substring(file.getName().length()-5);
            //文件名是 随机数加文件名的后5位
            sftp.put(new FileInputStream(file), fileName);
            sftp.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file == null ? null : "http://119.23.16.29:8090/img/imgheadpic/"+fileName;
    }

    /**
     * 下载文件
     */
    public void download(String downloadFileName) {
        try {
            sftp.cd(directory);

            File file = new File(saveFile);

            sftp.get(downloadFileName, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     *
     * @param deleteFile
     *            要删除的文件名字
     */
    public void delete(String deleteFile) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 列出目录下的文件
     *
     * @param directory
     *            要列出的目录
     * @return
     * @throws SftpException
     */
    public Vector listFiles(String directory)
            throws SftpException {
        return sftp.ls(directory);
    }

}

