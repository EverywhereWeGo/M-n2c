import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import ucar.ma2.Array;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class tocsv2 {
    private static int[] flag = null;
    private static int count = 0;

    public static void main(String[] args) {
        //初始化csvformat
        CSVFormat formator = CSVFormat.DEFAULT.withRecordSeparator("\n");

//        String filename = "geo_em.d01.nc";
        String filename = "CCTM_D502a_Linux3_x86_64gcc.AEROVIS.CMAQ-BENCHMARK_20190924";
//        String filename = "air.mon.anom.nc";
        String path = "/Users/everywherewego/IdeaProjects/n2c/src/main/resources/" + filename;
        NetcdfFile dataFile = null;
        try {
            dataFile = NetcdfFile.open(path, null);

            Map<String, Integer> dimevalue = new HashMap<String, Integer>();
            System.out.println("dimension:——————————————————————");
            List<Dimension> dimensions = dataFile.getDimensions();
            for (Dimension dimension : dimensions) {
                dimevalue.put(dimension.getFullName(), dimension.getLength());
                System.out.println(dimension.getLength());
                System.out.println(dimension);
            }
            System.out.println(dimevalue);

            System.out.println("variables:________________");
            List<Variable> variables = dataFile.getVariables();
            for (Variable variable : variables) {
                System.out.println(variable.getFullName());
            }

            for (Variable variable : variables) {
                createFile(dateToString(new Date(), "yyyy-MM-dd"));
                //创建FileWriter对象
                FileWriter fileWriter = new FileWriter(dateToString(new Date(), "yyyy-MM-dd") + "/" + filename + "_" + variable.getFullName() + ".csv");
                //创建CSVPrinter对象
                CSVPrinter printer = new CSVPrinter(fileWriter, formator);

                List<Dimension> dim = variable.getDimensions();
                ///第一行
                List<String> firstRow = new LinkedList<String>();
                //第一行第一列
                String dataname = variable.getFullName();
                String datatype = variable.getDataType().toString();
                firstRow.add(dataname + "(" + datatype + ")");
                //第一行第二列往后
                for (int i = 1; i < dim.size() + 1; i++) {
                    firstRow.add(dim.get(i - 1).getFullName());
                }

//              写入列头数据
                printer.printRecord(firstRow);
                printer.flush();

                //数据部分

                //第一列
                Variable v = dataFile.findVariable(variable.getFullName());
                Array a = v.read();
                //第二列往后
                int[] array = new int[dim.size()];
                for (int i = 0; i < array.length; i++) {
                    array[i] = dimevalue.get(firstRow.get(i + 1));
                    System.out.println(array[i]);
                }
                // 入参
                flag = new int[array.length - 1];
                // 执行
                printDeep(array, array.length, printer, a);

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void printDeep(int[] array, int length, CSVPrinter printer, Array dataArray) throws IOException {
        if (null == array || array.length == 0) {
            return;
        }
        // 当数组长度唯一，代表数据已经入最内层循环，即可 开始打印数据

        if (array.length == 1) {
            for (int i = 0; i < array[0]; i++) {
                List<String> stringArray = new LinkedList<String>();
                stringArray.add(dataArray.getFloat(count) + "");
                for (int j = 0; j < flag.length; j++) {
                    stringArray.add(flag[j] + 1 + "");
                }
                stringArray.add(i + 1 + "");
                System.out.println(stringArray);
                printer.printRecord(stringArray);
                stringArray.clear();
                printer.flush();
            }

            return;
        }
        // 剥离最外层循环，记录外层循环次数
        int index = array[0];
        for (int i = 0; i < index; i++) {
            // 分别记录每一层需要打印的数据
            flag[length - array.length] = i;
            // 定义新数组，来处理剥离外层循环后剩下的n维数组
            int[] arr = new int[array.length - 1];
            System.arraycopy(array, 1, arr, 0, array.length - 1);
            // 递归调用，实现一层一层剥离循环，达到最后实现用一维数组解决数据打印问题
            printDeep(arr, length, printer, dataArray);
        }
    }

    public static String dateToString(Date date, String format) throws Exception {
        if (date == null) {
            throw new Exception("dateToString:要转换的日期参数为空！");
        }
        String dateStr = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            dateStr = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateStr;
    }


    public static File createFile(String path) {
        String newPath = path;
        File file = new File(newPath);
        //如果文件目录不存在则创建目录
        if (!file.exists()) {
            if (!file.mkdir()) {
                System.out.println("当前路径不存在，创建失败");
                return null;
            }
        }
        System.out.println("创建成功" + newPath);
        return file;
    }

}
