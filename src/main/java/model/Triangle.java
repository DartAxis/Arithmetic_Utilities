package model;

import lombok.Getter;


@Getter
public class Triangle {
    private Double sideAB; // сторона AB
    private Double sideBC; // сторона BC
    private Double sideAC; // сторона АС
    private Double radiusIn; // радиус вписанной окружности
    private Double radiusOut; // радиус описанной окружности
    private Double medAB; // медиана проведенная к стороне AB
    private Double medBC; // медиана проведенная к стороне BC
    private Double medAC; // медиана проведенная к стороне AC
    private Double heightAB; // высота проведенная к стороне AB из вершины C
    private Double heightBC; // высота проведенная к стороне BC из вершины A
    private Double heightAC; // высота проведенная к стороне AC из вершины B
    private Double bisA; // биссектриса угла A
    private Double bisB; // биссектриса угла B
    private Double bisC; // биссектриса угла C
    private Boolean regular = false; // признак правильности треугольника
    private Boolean right = false; // признак прямоугольности треугольника
    private Boolean isosceles = false; // признак равнобедренности треугольника
    private Double angleA; // угол A в градусах
    private Double angleB; // угол B в градусах
    private Double angleC; // угол C в градусах
    private Double perimeter; // периметр треугольника
    private Double area; // площадь треугольника
    private StringBuilder decision = new StringBuilder(); //решение треугольника

    public Triangle() {

    }

    public Triangle(Double sAB, Double sBC, Double sAC) {
        this.sideAB = sAB;
        this.sideBC = sBC;
        this.sideAC = sAC;
        processTriangle();
    }


    private void processTriangle() {
        //если треугольник прямоугольный
        if (getRight()) {
            processRightTriangle();
        }
        if (getIsosceles()) {
            // processIsoscelesTriangle();

        }

        if (getRegular()) {
            //processRegularTriangle();
        }
        processTypicalTriangle();
    }

    private void processRightTriangle() {
        // вычисляем периметр если можно
        calculatePerimeter();
        // если известна гипотенуза и не вычислена медиана к гипотенузе то вычисляем
        processMedBC();
        // Если известен  первый катет и гипотенуза вычисляем второй
        processCatetAC();
        // Если известен  второй катет и гипотенуза вычисляем второй
        processCatetAB();
        // если известны катеты
        if (sideAB != null && sideAC != null) {
            //вычисляем площадь
            if (area == null) {
                decision.append("В прямоугольном треугольнике площадь равна половине произведения катетов. S=0.5*AB*AC. S=");
                setArea(sideAB * sideAC * 0.5);
                decision.append(area);
                decision.append("\n");
            }
            //вычисляем гипотенузу
            if (sideBC == null) {
                decision.append("По теореме пифагора находим гипотенузу BC=");
                setSideBC(Math.sqrt(sideAB * sideAB + sideAC * sideAC));
                decision.append(sideBC);
            }
            //вычисляем угол ABC
            if (angleB == null) {
                decision.append("Вычисляем угол ABC=");
                setAngleB(radianToGrad(Math.asin(sideAC / sideBC)));

            }
            //вычисляем угол ACB
            if (angleC == null) {
                decision.append("Вычисляем угол ACB=");
                setAngleC(radianToGrad(Math.asin(sideAB / sideBC)));
            }

        }
        if (heightBC == null && sideAB != null && sideBC != null) {
            Double be = sideAB * sideAB / sideBC;
            setHeightBC(Math.sqrt(sideAB * sideAB - be * be));
        }

    }

    private void processMedBC() {
        if (sideBC != null && medBC == null) {
            decision.append("В прямоугольном треугольнике, медиана проверенная к гипотенузе равна половине гипотенузы и равна=");
            decision.append(sideBC / 2);
            decision.append("\n");
            setMedBC(sideBC / 2);
        }
    }

    private void processCatetAC() {
        if (sideAB != null && sideBC != null && sideAC == null) {
            setSideAC(Math.sqrt(sideBC * sideBC - (sideAB * sideAB)));
        }
    }

    private void processCatetAB() {
        if (sideAC != null && sideBC != null && sideAB == null) {
            setSideAB(Math.sqrt(sideBC * sideBC - (sideAC * sideAC)));
        }
    }

    private void calculatePerimeter() {
        if (perimeter == null) {
            perimeter = sideAB + sideBC + sideAC;
            decision.append("Найдём периметр треугольника ABC путем сложения всех длин его сторон,\nпериметр треугольника ABC=AB+BC+AC. ");
            decision.append("P=");
            decision.append(perimeter);
            decision.append(".\n");
        }
    }

    private void calculateAreaGeron() {
        if (area == null && perimeter != null) {
            Double semiPerimeter = perimeter / 2;
            setArea(Math.sqrt(semiPerimeter * (semiPerimeter - sideAB) * (semiPerimeter - sideBC) * (semiPerimeter - sideAC)));
            decision.append("По формуле Герона находим площадь треугольника ABC\n");
            decision.append("S=");
            decision.append(area);
            decision.append("\n");
        }
    }

    private void calculateRadiusIn() {
        if (radiusIn == null && perimeter != null && area != null) {
            setRadiusIn(area / (perimeter * 0.5));
            decision.append("Вычисляем радиус вписанной окружности он равен r=S/p. r=");
            decision.append(area);
            decision.append("/");
            decision.append(perimeter / 2);
            decision.append("=");
            decision.append(radiusIn);
            decision.append("\n");
        }
    }

    private void checkRegular() {
        if (sideAB.equals(sideBC) && sideAB.equals(sideAC) && !getRegular()) {
            decision.append("Поскольку AB=BC=AC, то треугольник ABC правильный.\n");
            setRegular(true);
        }
    }

    private void checkRight() {
        if (sideBC.equals(Math.sqrt(sideAB * sideAB + sideAC * sideAC)) && !getRight()) {
            decision.append("Треугольник ABC является прямоугольным по обратной теореме пифагора.\n");
            setRight(true);
            setHeightAC(sideAB);
            setHeightAB(sideAC);
        }
    }

    private void checkIsosceles() {
        if (sideAB.equals(sideBC) && !getIsosceles()) {
            decision.append("Так как AB=BC, то треугольник ABC равнобедренный.\n");
            setIsosceles(true);
        }
    }

    private void calculateRadiusOut() {
        if (radiusOut == null) {
            setRadiusOut((sideAB * sideBC * sideAC) / (4 * area));
            decision.append("Вычисляем радиус описанной окружности он равен R=(AB*BC*AC)/(4*S). R=");
            decision.append(String.format("(%s*%s*%s)/(4*%s)=%s%n",sideAB,sideBC,sideAC,area,radiusOut));
        }
    }

    private void processTypicalTriangle() {
        if (sideAB != null && sideBC != null && sideAC != null) {
            //проверяем по 3-м сторонам правильный треугольник или нет
            checkRegular();
            //проверяем а не является ли треугольник прямоугольным
            checkRight();
            //проверяем яляется ли треугольник равнобедренным
            checkIsosceles();

            //если известны все три стороны то периметр найти сразу сам бог велел
            calculatePerimeter();

            //по формуле Герона находим площадь треугольника
            calculateAreaGeron();

            //вычисляем радиус вписанной окружности если вычислены площадь и периметр
            calculateRadiusIn();

            //вычисляем радиус описанной окружности если нашли все стороны
            calculateRadiusOut();
        }
    }


    public void setSideAB(Double sideAB) {
        this.sideAB = sideAB;
        processTriangle();
    }

    public void setSideBC(Double sideBC) {
        this.sideBC = sideBC;
        processTriangle();
    }

    public void setSideAC(Double sideAC) {
        this.sideAC = sideAC;
        processTriangle();
    }

    public void setRadiusIn(Double radiusIn) {
        this.radiusIn = radiusIn;
        processTriangle();
    }

    public void setRadiusOut(Double radiusOut) {
        this.radiusOut = radiusOut;
        processTriangle();
    }

    public void setMedAB(Double medAB) {
        this.medAB = medAB;
        processTriangle();
    }

    public void setMedBC(Double medBC) {
        this.medBC = medBC;
        processTriangle();
    }

    public void setMedAC(Double medAC) {
        this.medAC = medAC;
        processTriangle();
    }

    public void setHeightAB(Double heightAB) {
        this.heightAB = heightAB;
        processTriangle();
    }

    public void setHeightBC(Double heightBC) {
        this.heightBC = heightBC;
        processTriangle();
    }

    public void setHeightAC(Double heightAC) {
        this.heightAC = heightAC;
        processTriangle();
    }

    public void setBisA(Double bisA) {
        this.bisA = bisA;
        processTriangle();
    }

    public void setBisB(Double bisB) {
        this.bisB = bisB;
        processTriangle();
    }

    public void setBisC(Double bisC) {
        this.bisC = bisC;
        processTriangle();
    }

    public void setRegular(Boolean regular) {
        this.regular = regular;
        if (sideAB != null && sideBC == null && sideAC == null) {
            sideBC = sideAB;
            sideAC = sideAB;
        } else if (sideBC != null && sideAB == null && sideAC == null) {
            sideAB = sideBC;
            sideAC = sideBC;
        } else if (sideAC != null && sideAB == null && sideBC == null) {
            sideAB = sideAC;
            sideBC = sideAC;
        }
        angleA = angleB = angleC = 60D;
        setIsosceles(true);

    }

    public void setRight(Boolean right) {
        this.right = right;
        if (angleA == null) {
            angleA = 90D;
        }
        processTriangle();
    }

    public void setIsosceles(Boolean isosceles) {
        this.isosceles = isosceles;
        if (sideAB != null && sideBC == null) {
            sideBC = sideAB;
        } else if (sideBC != null && sideAB == null) {
            sideAB = sideBC;
        }
        processTriangle();
    }

    public void setAngleA(Double angleA) {
        this.angleA = angleA;
        processTriangle();
    }

    public void setAngleB(Double angleB) {
        this.angleB = angleB;
        decision.append(angleB);
        decision.append("\n");
        processTriangle();
    }

    public void setAngleC(Double angleC) {
        this.angleC = angleC;
        decision.append(angleC);
        decision.append("\n");
        processTriangle();
    }

    public void setPerimeter(Double perimeter) {
        this.perimeter = perimeter;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public static Double gradToRadian(Double grad) {
        return (Math.PI * grad) / 180;
    }

    public static Double radianToGrad(Double rad) {
        return (double) Math.round((rad * 180) / Math.PI);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "\nsideAB=" + sideAB +
                ",\n sideBC=" + sideBC +
                ",\n sideAC=" + sideAC +
                ",\n radiusIn=" + radiusIn +
                ",\n radiusOut=" + radiusOut +
                ",\n medAB=" + medAB +
                ",\n medBC=" + medBC +
                ",\n medAC=" + medAC +
                ",\n heightAB=" + heightAB +
                ",\n heightBC=" + heightBC +
                ",\n heightAC=" + heightAC +
                ",\n bisA=" + bisA +
                ",\n bisB=" + bisB +
                ",\n bisC=" + bisC +
                ",\n regular=" + regular +
                ",\n right=" + right +
                ",\n isosceles=" + isosceles +
                ",\n angleA=" + angleA +
                ",\n angleB=" + angleB +
                ",\n angleC=" + angleC +
                ",\n perimeter=" + perimeter +
                ",\n area=" + area +
                '}';
    }
}
