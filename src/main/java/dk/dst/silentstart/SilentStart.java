package dk.dst.silentstart;

import com.sun.jna.platform.win32.WinBase.PROCESS_INFORMATION;
import com.sun.jna.platform.win32.WinBase.STARTUPINFO;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.Kernel32;

public class SilentStart {
   private static final int STARTF_USESTDHANDLES = 0x00000100;

   public static void start(String cmd) throws Exception {
      String szCmdline = cmd;

      PROCESS_INFORMATION processInformation = new PROCESS_INFORMATION();
      STARTUPINFO startupInfo = new STARTUPINFO();
      startupInfo.cb = new DWORD(processInformation.size());
      startupInfo.dwFlags |= STARTF_USESTDHANDLES;

      if (!Kernel32.INSTANCE.CreateProcess(null, szCmdline, null, null, true, new DWORD(0x00000020), null, null,
            startupInfo, processInformation)) {
         throw new Exception("General failure");
      } else {
         com.sun.jna.platform.win32.Kernel32.INSTANCE.WaitForSingleObject(processInformation.hProcess, 0xFFFFFFFF);
      }
   }

   public static void main(String[] args) {
      try {
         start("c:\\code\\ssp\\py\\krypto.exe a b c");
      } catch (Exception ex) {
         System.out.println("error" + ex.getMessage());
      }
   }
}