//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import ucar.ma2.Array;
//import ucar.nc2.Dimension;
//import ucar.nc2.NetcdfFile;
//import ucar.nc2.Variable;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.List;
//
//public class toexcel {
//
//    public static void main(String[] args) {
////        Scanner input = new Scanner(System.in);
////        System.out.print("Enter a number for radius: ");
////        double radius = input.next();
////        double area;
////        area = radius * radius * 3.14159;
////        System.out.println("The area for the circle of radius " + radius + " is " + area);
//
//        //创建新的工作薄
//        Workbook wb = new XSSFWorkbook();
////        String filename = "geo_em.d01.nc";
//        String filename = "CCTM_D502a_Linux3_x86_64gcc.AEROVIS.CMAQ-BENCHMARK_20190924";
//        String path = "/Users/everywherewego/IdeaProjects/n2c/src/main/resources/" + filename;
//        NetcdfFile dataFile = null;
//        try {
//            dataFile = NetcdfFile.open(path, null);
//
//            System.out.println("dimension:——————————————————————");
//            List<Dimension> dimensions = dataFile.getDimensions();
//            for (Dimension dimension : dimensions) {
//                System.out.println(dimension);
//            }
//
//            System.out.println("variables:________________");
//            List<Variable> variables = dataFile.getVariables();
//
//            for (Variable variable : variables) {
//                System.out.println(variable.getFullName());
//                Sheet sheet = wb.createSheet(variable.getFullName());
//
//                List<Dimension> dim = variable.getDimensions();
//
//                ///第一行
//                Row row = sheet.createRow(0);
//                String dataname = variable.getFullName();
//                String datatype = variable.getDataType().toString();
//                //第一列
//                row.createCell(0).setCellValue(dataname + "(" + datatype + ")");
//                //第二列往后
//                int k = 1;
//                for (Dimension i : dim) {
//                    System.out.println(i.getFullName());
//                    row.createCell(k++).setCellValue(i.getFullName());
//                }
//
//                //数据部分
//                Variable v = dataFile.findVariable(variable.getFullName());
//                Array a = v.read();
//                System.out.println(a.getSize());
//
//                //从第二行开始
//                for (int i = 1; i <= a.getSize(); i++) {
//                    //如果数据大于1048576行¬
////                    if (i % 1048576 == 0) {
////                        int g = i / 1048576;
//                        dim.size();
//                        Row rows = sheet.createRow(i);
//                        if ("char".equals(datatype)) {
//                            rows.createCell(0).setCellValue(a.getChar(i - 1) + "");
//                            System.out.println(a.getChar(i - 1));
//                        } else if ("float".equals(datatype)) {
//                            rows.createCell(0).setCellValue(a.getFloat(i - 1));
////                        }
//                    }
//                }
//            }
//
//            FileOutputStream fos = new FileOutputStream(filename + ".xlsx");
//            wb.write(fos);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private static void setCellValueByType(String datatype, Row rows, Array a, int i) {
//
//
//    }
//
//}
