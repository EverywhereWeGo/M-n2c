import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ucar.ma2.Array;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class tocsv {
    //CSV文件分隔符
    private final static String NEW_LINE_SEPARATOR = "\n";
    //初始化csvformat
    CSVFormat formator = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

    public static void main(String[] args) throws IOException {
//        Scanner input = new Scanner(System.in);
//        System.out.print("Enter a number for radius: ");
//        double radius = input.next();
//        double area;
//        area = radius * radius * 3.14159;
//        System.out.println("The area for the circle of radius " + radius + " is " + area);


        //初始化csvformat
        CSVFormat formator = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);


//        if(null!=data){
//            //循环写入数据
//            for(String[] lineData:data){
//
//                printer.printRecord(lineData);
//
//            }
//        }
//
//        System.out.println("CSV文件创建成功,文件路径:"+filePath);


        //创建新的工作薄
        Workbook wb = new XSSFWorkbook();
//        String filename = "geo_em.d01.nc";
        String filename = "CCTM_D502a_Linux3_x86_64gcc.AEROVIS.CMAQ-BENCHMARK_20190924";
        String path = "/Users/everywherewego/IdeaProjects/n2c/src/main/resources/" + filename;
        NetcdfFile dataFile = null;
        try {
            dataFile = NetcdfFile.open(path, null);

            System.out.println("dimension:——————————————————————");
            List<Dimension> dimensions = dataFile.getDimensions();
            for (Dimension dimension : dimensions) {
                System.out.println(dimension);
            }

            System.out.println("variables:________________");
            List<Variable> variables = dataFile.getVariables();


            for (Variable variable : variables) {

                //创建FileWriter对象
                FileWriter fileWriter = new FileWriter(filename + "_" + variable.getFullName() + ".csv");

                //创建CSVPrinter对象
                CSVPrinter printer = new CSVPrinter(fileWriter, formator);

                List<Dimension> dim = variable.getDimensions();


                ///第一行
                String[] firstRow = new String[dim.size() + 1];
                //第一行第一列
                String dataname = variable.getFullName();
                String datatype = variable.getDataType().toString();
                firstRow[0] = dataname + "(" + datatype + ")";
                //第一行第二列往后
                for (int i = 1; i < firstRow.length; i++) {
                    firstRow[i] = dim.get(i - 1).getFullName();
                }
                //写入列头数据
                System.out.println(firstRow[dim.size()]);
                printer.printRecord(firstRow);


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
            }

//            FileOutputStream fos = new FileOutputStream(filename + ".xlsx");
//            wb.write(fos);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void setCellValueByType(String datatype, Row rows, Array a, int i) {


    }

}
