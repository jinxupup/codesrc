package com.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jjb.unicorn.facility.util.StringUtils;


/**
 * @ClassName TestTxt
 * @Description TODO
 * @Author smh
 * Date 2019/1/15 16:59
 * Version 1.0
 */

public class TestTxtToExcel {

        static String path="C:\\Users\\hp\\Desktop\\51\\20190419\\v2.txt";
        public static void main(String[] args) throws IOException {
            readF1(path);
        }
        public static  void readF1(String filePath) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filePath),"gbk"));
            String line =null;
            String[] arr=null;
            List<JSONObject> list1=new ArrayList<>();
            while ( (line=br.readLine())!= null){
                JSONObject resJs = new JSONObject();
                resJs = JSON.parseObject(line);
                list1.add(resJs);
            }
                HSSFWorkbook workbook = new HSSFWorkbook();
                HSSFSheet sheet = workbook.createSheet("表");
                sheet.autoSizeColumn((short) 0); //调整第一列宽度
                sheet.autoSizeColumn((short) 10);
                HSSFRow row = sheet.createRow(0);
                HSSFCellStyle style = workbook.createCellStyle();
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
                String[] rowHeaders = {"AppNo", "Buscore", "FinalTotScore",
                        "FinalAuditresult", "RejectReason","RiskLevel1",
                        "RiskLevel2", "FinalCreditlimt",
                        "FinalOtherLimit", "d",
                        "AuditisriskJj","NewBuscore","NewRiskLevel","NewFinalCreditlimt"};
                HSSFCell cell;
            for (int i = 0; i < rowHeaders.length; i++) {
                cell = row.createCell(i);
                cell.setCellValue(rowHeaders[i]);
                cell.setCellStyle(style);
            }

            for (int i = 0; i < list1.size(); i++) {
                JSONObject resJson=list1.get(i);
                //获取app_no
                String app_no="";
                if(resJson.containsKey("requestParam") ){
                    JSONObject js1 = resJson.getJSONObject("requestParam");
                    if (js1.containsKey("app_no")){
                         app_no = js1.getString("app_no");
                        if (StringUtils.isEmpty(app_no)){
                            app_no="";
                        }
                    }
                }
                //获取buscore
                String buscore="";
                if(resJson.containsKey("decisionResult") ){
                    JSONObject js1 = resJson.getJSONObject("decisionResult");
                    if (js1.containsKey("resultMap")){
                        JSONObject js2=js1.getJSONObject("resultMap");
                        if (js2.containsKey("buscore")){
                            buscore = js2.getString("buscore");
                            if (StringUtils.isEmpty(buscore)){
                                buscore="";
                            }
                        }
                    }
                }

                //获取final_tot_score
                String final_tot_score="";
                if(resJson.containsKey("decisionResult") ){
                    JSONObject js1 = resJson.getJSONObject("decisionResult");
                    if (js1.containsKey("resultMap")){
                        JSONObject js2=js1.getJSONObject("resultMap");
                        if (js2.containsKey("final_tot_score")){
                            final_tot_score = js2.getString("final_tot_score");
                            if (StringUtils.isEmpty(final_tot_score)){
                                final_tot_score="";
                            }
                        }
                    }
                }

                //获取reject_reason
                String reject_reason="";
                StringBuilder reason=new StringBuilder();


                if(resJson.containsKey("decisionResult") ){
                    JSONObject js1 = resJson.getJSONObject("decisionResult");
                    if (js1.containsKey("resultMap")){
                        JSONObject js2=js1.getJSONObject("resultMap");
                        if (js2.containsKey("reject_reason")){
                            JSONArray jsonArray= js2.getJSONArray("reject_reason");
                            if (jsonArray==null){
                                reject_reason="";
                            }
                            for (int j=0;j<jsonArray.size();j++){
                                reason.append(jsonArray.getString(j)+",");
                            }
                            reject_reason=reason.toString();
                        }
                    }
                }

                //获取final_auditresult
                String final_auditresult="";
                if(resJson.containsKey("decisionResult") ){
                    JSONObject js1 = resJson.getJSONObject("decisionResult");
                    if (js1.containsKey("resultMap")){
                        JSONObject js2=js1.getJSONObject("resultMap");
                        if (js2.containsKey("final_auditresult")){
                            final_auditresult = js2.getString("final_auditresult");
                            if (StringUtils.concat(final_auditresult,"黑名单")){
                                reject_reason="黑名单拒绝";
                            }
                            if (StringUtils.concat(final_auditresult,"通过")){
                                final_auditresult="1";
                            }else if (StringUtils.concat(final_auditresult,"拒绝")){
                                final_auditresult="2";
                            }
                            if (StringUtils.isEmpty(final_auditresult)){
                                final_auditresult="";
                            }
                        }
                    }else if (js1.containsKey("result")) {

                        final_auditresult = js1.getString("result");
                        if (StringUtils.concat(final_auditresult,"黑名单")){
                            reject_reason="黑名单拒绝";
                        }
                        if (StringUtils.concat(final_auditresult,"拒绝")){
                            final_auditresult="2";
                        }else if (StringUtils.concat(final_auditresult,"通过")){
                            final_auditresult="1";
                        }
                        if (StringUtils.isEmpty(final_auditresult)){
                            final_auditresult="未知错误";
                        }
                    }
                }

                //获取RiskLevel1
                String risklevel1="";
                if(resJson.containsKey("decisionResult") ){
                    JSONObject js1 = resJson.getJSONObject("decisionResult");
                    if (js1.containsKey("resultMap")){
                        JSONObject js2=js1.getJSONObject("resultMap");
                        if (js2.containsKey("risklevel1")){
                            risklevel1 = js2.getString("risklevel1");
                            if (StringUtils.isEmpty(risklevel1)){
                                risklevel1="";
                            }
                        }
                    }
                }
                //获取risklevel2
                String risklevel2="";
                if(resJson.containsKey("decisionResult") ){
                    JSONObject js1 = resJson.getJSONObject("decisionResult");
                    if (js1.containsKey("resultMap")){
                        JSONObject js2=js1.getJSONObject("resultMap");
                        if (js2.containsKey("risklevel2")){
                            risklevel2 = js2.getString("risklevel2");
                            if (StringUtils.isEmpty(risklevel2)){
                                risklevel2="";
                            }
                        }
                    }
                }
                //获取final_creditlimt
                String final_creditlimt="";
                if(resJson.containsKey("decisionResult") ){
                    JSONObject js1 = resJson.getJSONObject("decisionResult");
                    if (js1.containsKey("resultMap")){
                        JSONObject js2=js1.getJSONObject("resultMap");
                        if (js2.containsKey("final_creditlimt")){
                            final_creditlimt = js2.getString("final_creditlimt");
                            if (StringUtils.isEmpty(final_creditlimt)){
                                final_creditlimt="";
                            }
                        }
                    }
                }
                //获取 final_other_limit
                String final_other_limit="";
                if(resJson.containsKey("decisionResult") ){
                    JSONObject js1 = resJson.getJSONObject("decisionResult");
                    if (js1.containsKey("resultMap")){
                        JSONObject js2=js1.getJSONObject("resultMap");
                        if (js2.containsKey("final_other_limit")){
                            final_other_limit = js2.getString("final_other_limit");
                            if (StringUtils.isEmpty(final_other_limit)){
                                final_other_limit="";
                            }
                        }
                    }
                }
                //获取adjustIndex
                String adjustIndex="";
                if(resJson.containsKey("decisionResult") ){
                    JSONObject js1 = resJson.getJSONObject("decisionResult");
                    if (js1.containsKey("resultMap")){
                        JSONObject js2=js1.getJSONObject("resultMap");
                        if (js2.containsKey("adjustIndex")){
                            adjustIndex = js2.getString("adjustIndex");
                            if (StringUtils.isEmpty(adjustIndex)){
                                adjustIndex="";
                            }
                        }
                    }
                }
                //获取auditisrisk_jj
                String auditisrisk_jj="";
                if(resJson.containsKey("decisionResult") ){
                    JSONObject js1 = resJson.getJSONObject("decisionResult");
                    if (js1.containsKey("resultMap")){
                        JSONObject js2=js1.getJSONObject("resultMap");
                        if (js2.containsKey("auditisrisk_jj")){
                            auditisrisk_jj = js2.getString("auditisrisk_jj");
                            if (StringUtils.isEmpty(auditisrisk_jj)){
                                auditisrisk_jj="";
                            }
                        }
                    }
                }

                //获取new_buscore
                String new_buscore="";
                if(resJson.containsKey("decisionResult") ){
                    JSONObject js1 = resJson.getJSONObject("decisionResult");
                    if (js1.containsKey("resultMap")){
                        JSONObject js2=js1.getJSONObject("resultMap");
                        if (js2.containsKey("new_buscore")){
                            new_buscore = js2.getString("new_buscore");
                            if (StringUtils.isEmpty(new_buscore)){
                                new_buscore="";
                            }
                        }
                    }
                }

                //获取new_riskLevel
                String new_riskLevel="";
                if(resJson.containsKey("decisionResult") ){
                    JSONObject js1 = resJson.getJSONObject("decisionResult");
                    if (js1.containsKey("resultMap")){
                        JSONObject js2=js1.getJSONObject("resultMap");
                        if (js2.containsKey("new_riskLevel")){
                            new_riskLevel = js2.getString("new_riskLevel");
                            if (StringUtils.isEmpty(new_riskLevel)){
                                new_riskLevel="";
                            }
                        }
                    }
                }

                //获取new_riskLevel
                String new_final_creditlimt="";
                if(resJson.containsKey("decisionResult") ){
                    JSONObject js1 = resJson.getJSONObject("decisionResult");
                    if (js1.containsKey("resultMap")){
                        JSONObject js2=js1.getJSONObject("resultMap");
                        if (js2.containsKey("new_final_creditlimt")){
                            new_final_creditlimt = js2.getString("new_final_creditlimt");
                            if (StringUtils.isEmpty(new_final_creditlimt)){
                                new_final_creditlimt="";
                            }
                        }
                    }
                }
                row = sheet.createRow(i + 1);
                cell = row.createCell(0);
                cell.setCellValue(app_no);
                cell = row.createCell(1);
                cell.setCellValue(buscore);
                cell = row.createCell(2);
                cell.setCellValue(final_tot_score);
                cell = row.createCell(3);
                cell.setCellValue(final_auditresult);
                cell = row.createCell(4);
                cell.setCellValue(reject_reason);
                cell = row.createCell(5);
                cell.setCellValue(risklevel1);
                cell = row.createCell(6);
                cell.setCellValue(risklevel2);
                cell = row.createCell(7);
                cell.setCellValue(final_creditlimt);
                cell = row.createCell(8);
                cell.setCellValue(final_other_limit);
                cell = row.createCell(9);
                cell.setCellValue(adjustIndex);
                cell = row.createCell(10);
                cell.setCellValue(auditisrisk_jj);
                cell = row.createCell(11);
                cell.setCellValue(new_buscore);
                cell = row.createCell(12);
                cell.setCellValue(new_riskLevel);
                cell = row.createCell(13);
                cell.setCellValue(new_final_creditlimt);
            }
                try {
                    Date date = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                    String formatDate = dateFormat.format(date);
                    String path = "D:/"+"policy_data_"+formatDate+"(V2)"+".xls";
                    System.out.println(path);
                    FileOutputStream fout = new FileOutputStream(path);
                    workbook.write(fout);
                    fout.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }

            br.close();
        }

    }



