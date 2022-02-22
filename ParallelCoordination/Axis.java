package univ.cis.cs490.ParallelCoordination;


import java.util.ArrayList;
import java.util.List;

public class Axis {
 enum ColumnType{
  NUMERIC, TEXT
 }

 String columnName;
 ColumnType type;
 List<String> stringData;
 List<Double> numberData;

 public Axis(String name,ColumnType t){
  columnName = name;
  type = t;
  stringData = new ArrayList<>();
  numberData = new ArrayList<>();
 }
}
