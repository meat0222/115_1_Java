# 第四週 AI 使用紀錄

| 欄位 | 記錄 |
|---|---|
| 使用工具 | Claude Code（VS Code 擴充功能） |
| 使用目的與提問摘要 | 請 AI 完成 starter/MissionLog.java 的 TODO 1–5（逐筆記錄、指令計數、回放、摘要、最低能源），整理 java-04/src 目錄結構，並協助填寫 LAB_REPORT 各節答案與說明 |
| AI 建議與本人處理 | 採納 AI 的 TODO 實作：TODO 2 每步把 command、energy、result 同時 add 到三條清單（拒絕、無效、返回也記錄）；TODO 1 保留 `command >= 0 && command < counts.length` 範圍檢查；TODO 3 以同一索引讀三條清單；TODO 4 用 Collections.frequency 算 OK 與 RETURN；TODO 5 先判斷 isEmpty，空清單印 N/A。F2 判讀時拒絕「HashSet 保存指令」：任務會重複探索，日誌需要保留順序與重複 |
| 實際驗證結果 | 本人在 java-04 以 `javac -encoding UTF-8 -d out src/*.java` 編譯，執行 MissionLog 預設 {1,1,2} 得 6/5/3 SUCCESS，並執行 ArrayBasics 等範例對照。E 節六條路線、F1 五個案例與 F3 反例由 AI 在副本執行，輸出記於 LAB_REPORT E、F。F3 刪除範圍檢查後 {3,9,0} 出現 ArrayIndexOutOfBoundsException: 9，恢復後 counts=[1, 0, 0, 1]、RETURNED。繳交前恢復 {1,1,2} |

只記錄實際發生的操作。AI 宣稱正確不能取代編譯執行。
