# 第五週 AI 使用紀錄

| 欄位 | 本人紀錄 |
|---|---|
| 使用工具／方式 | 真實 AI 服務：Claude Code（VS Code 擴充功能） |
| 目的與提問摘要 | 依第五週簡報完成 starter 的 TODO 1–6（MissionStep 建構子、getter、isSuccessful；MissionLog 新增記錄、回放、摘要）；請 AI 說明執行步驟與錯誤原因（終端機不在作業資料夾、script 打錯字、編譯錯誤訊息）；SampleCase F1 設計卡與 F2 偏差審查、改寫 Prompt；整理 LAB_REPORT 草稿 |
| 建議與本人處理 | 採納 TODO 1–6 實作：isSuccessful 用 "OK".equals(result) 比內容而非 ==，TODO 4 放在 switch 之後才記錄行動後能源。F1／F2 由 AI 提供答案，本人閱讀後確認符合第 36 頁需求（容量修正、整批拒絕、卸空回傳、不提供 setter）。F3 選項二依 AI 指引在 repo 外副本自行修改與執行 |
| 本人實際驗證 | 本人在 VS Code 終端機編譯執行：{1,1,2} 得 6/5/3 SUCCESS、battery=30.0；{3,9,0} 得 20/0/0 RETURNED；四個 examples 輸出皆與 README 相符；CreateStepDemo 加入 new StepCard() 重現編譯錯誤後恢復；F3 錯誤副本 new String("OK") 得 isSuccessful=false，修正後為 true 並重測兩條路線。詳見 LAB_REPORT B–F3。F1／F2 為紙上推論，SampleCase 未實作、未實測 |

AI 寫的程式皆經本人編譯執行確認，未上傳個資、密碼或憑證。
