package wiseSaying;

import java.io.*;
import java.util.Scanner;

public class TestUtil {
    public static void eraseFile() {
        // 삭제할 디렉토리 경로 설정
        File directory = new File("db/wiseSaying");

        // 디렉토리가 존재하고 실제 디렉토리인지 확인
        if (directory.exists() && directory.isDirectory()) {
            // 디렉토리 내의 모든 파일과 디렉토리 목록 가져오기
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    // 파일인 경우 삭제
                    if (file.isFile()) {
                        boolean deleted = file.delete();
                        if (deleted) {
                            System.out.println("파일 삭제됨: " + file.getName());
                        } else {
                            System.out.println("파일 삭제 실패: " + file.getName());
                        }
                    }
                    // 하위 디렉토리가 있을 경우 재귀적으로 삭제하려면 아래 주석을 해제하세요.
                    /*
                    else if (file.isDirectory()) {
                        deleteDirectoryRecursively(file);
                    }
                    */
                }
                System.out.println("모든 파일 삭제를 시도했습니다.");
            } else {
                System.out.println("디렉토리 내에 삭제할 파일이 없습니다.");
            }
        } else {
            System.out.println("디렉토리가 존재하지 않거나 디렉토리가 아닙니다: " + directory.getAbsolutePath());
        }
    }
}
