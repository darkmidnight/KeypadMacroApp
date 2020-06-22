#define COL_1 7
#define COL_2 8

#define ROW_1 3
#define ROW_2 5
#define ROW_3 6
#define ROW_4 9

#define K_COL_1 14
#define K_COL_2 16

#define K_ROW_1 18
#define K_ROW_2 19
#define K_ROW_3 20
#define K_ROW_4 21

byte rows[] = {21, 18, 19, 20};
const int rowCount = sizeof(rows) / sizeof(rows[0]);

byte cols[] = {14, 16};
const int colCount = sizeof(cols) / sizeof(cols[0]);

byte keys[colCount][rowCount];

void setup() {
  Serial.begin(9600);
  for (int x = 0; x < rowCount; x++) {
    pinMode(rows[x], INPUT);
  }
  for (int x = 0; x < colCount; x++) {
    pinMode(cols[x], INPUT_PULLUP);
  }
  lightTest(0b00000000);
}

void readMatrix() {
  // iterate the columns
  for (int colIndex = 0; colIndex < colCount; colIndex++) {
    // col: set to output to low
    byte curCol = cols[colIndex];
    pinMode(curCol, OUTPUT);
    digitalWrite(curCol, LOW);

    // row: interate through the rows
    for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
      byte rowCol = rows[rowIndex];
      pinMode(rowCol, INPUT_PULLUP);
      keys[colIndex][rowIndex] = digitalRead(rowCol);
      pinMode(rowCol, INPUT);
    }
    // disable the column
    pinMode(curCol, INPUT);
  }
}

byte prevByte = 0;
void printMatrix() {
  byte aByte = 0;
  int i = 0;
  for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
    for (int colIndex = 0; colIndex < colCount; colIndex++) {
      //Serial.print(keys[colIndex][rowIndex]);
      if (keys[colIndex][rowIndex] == 1) {
        aByte = aByte | (0 << i);
      } else {
        aByte = aByte | (1 << i);
      }
      i++;
    }
  }
  if (aByte != prevByte) {

    Serial.write(aByte);
    Serial.flush();
  }
  prevByte = aByte;
}

byte lightVal;
void loop() {
  if (Serial.available() > 0) {
    lightVal = Serial.read();
  }
  lightTest(lightVal);
  readMatrix();
  printMatrix();
}

void lightTest(int aByte) {
  pinMode(COL_1, INPUT);
  pinMode(COL_2, OUTPUT);
  
  analogWrite(ROW_1, ((aByte & (1 << (7))) == (1 << (7))) ? 255 : 0);
  analogWrite(ROW_2, ((aByte & (1 << (5))) == (1 << (5))) ? 255 : 0);
  analogWrite(ROW_3, ((aByte & (1 << (3))) == (1 << (3))) ? 255 : 0);
  analogWrite(ROW_4, ((aByte & (1 << (0))) == (1 << (0))) ? 255 : 0);
  delay(10);

  pinMode(COL_2, INPUT);
  pinMode(COL_1, OUTPUT);
  
  analogWrite(ROW_1, ((aByte & (1 << (6))) == (1 << (6))) ? 255 : 0);
  analogWrite(ROW_2, ((aByte & (1 << (4))) == (1 << (4))) ? 255 : 0);
  analogWrite(ROW_3, ((aByte & (1 << (1))) == (1 << (1))) ? 255 : 0);
  analogWrite(ROW_4, ((aByte & (1 << (2))) == (1 << (2))) ? 255 : 0);
  delay(10);
}

