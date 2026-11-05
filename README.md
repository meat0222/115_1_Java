# 115_1_Java

115 學年度第一學期 Java 課程作業。

| 資料夾 | 內容 |
|---|---|
| [115_09_18](115_09_18/) | HelloCourse |
| [115_10_02](115_10_02/) | GreetingApp |
| [115_10_29](115_10_29/) | 第四週：火星探測車任務日誌：陣列、ArrayList、Collections |

## 第四週編譯與執行

```
cd 115_10_29/java-04
mkdir out        # JDK 8 需先建立輸出資料夾
javac -encoding UTF-8 -d out src/*.java
java -cp out MissionLog
```

預設腳本 {1,1,2} 的結果：`final energy=6, samples=5, actionCount=3 -> SUCCESS`。

實作紀錄見 [LAB_REPORT.md](115_10_29/LAB_REPORT.md)，AI 使用紀錄見 [AI_USAGE.md](115_10_29/AI_USAGE.md)。
