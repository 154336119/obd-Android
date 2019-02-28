package com.slb.ttdandroidframework.util.config;


/**
 * Created by Gifford on 2017/12/4.
 * 模式6 单位编码、系数、单位
 */

public enum Mode6Enum {
	x01(Mode6UnitCode.x01,1,"",0),
	x02(Mode6UnitCode.x02,0.1,"",0),
	x03(Mode6UnitCode.x03,0.01,"",0),
	x04(Mode6UnitCode.x04,0.001,"",0),
	x05(Mode6UnitCode.x05,0.000035,"",0),
	x06(Mode6UnitCode.x06,0.00035,"",0),
	x07(Mode6UnitCode.x07,0.25,"",0),
	x08(Mode6UnitCode.x08,0.01,"km/h",0),
	x09(Mode6UnitCode.x09,1,"km/h",0),
	x0A(Mode6UnitCode.x0A,0.000122,"v",0),
	x0B(Mode6UnitCode.x0B,0.001,"v",0),
	x0C(Mode6UnitCode.x0C,0.01,"",0),
	x0D(Mode6UnitCode.x0D,0.0000039,"a",0),
	x0E(Mode6UnitCode.x0E,0.001,"a",0),
	x0F(Mode6UnitCode.x0F,0.01,"a",0),

	x10(Mode6UnitCode.x10,0.001,"s",0),
	x11(Mode6UnitCode.x11,0.1,"s",0),
	x12(Mode6UnitCode.x12,1,"s",0),
	x13(Mode6UnitCode.x13,1,"ohm",0),
	x14(Mode6UnitCode.x14,1,"ohm",0),
	x15(Mode6UnitCode.x15,1,"kohm",0),
	x16(Mode6UnitCode.x16,0.1,"C",-40),
	x17(Mode6UnitCode.x17,0.01,"kPa",0),
	x18(Mode6UnitCode.x18,0.0117,"kPa",0),
	x19(Mode6UnitCode.x19,0.079,"kPa",0),
	x1A(Mode6UnitCode.x1A,1,"kPa",0),
	x1B(Mode6UnitCode.x1B,10,"kPa",0),
	x1C(Mode6UnitCode.x1C,0.01,"°",0),
	x1D(Mode6UnitCode.x1D,0.5,"°",0),
	x1E(Mode6UnitCode.x1E,0.0000305,"",0),
	x1F(Mode6UnitCode.x1F,0.05,"",0),

	x20(Mode6UnitCode.x10,0.0039,"",0),
	x21(Mode6UnitCode.x11,1,"",0),
	x22(Mode6UnitCode.x22,1,"",0),
	x23(Mode6UnitCode.x23,1,"",0),
	x24(Mode6UnitCode.x24,1,"",0),
	x25(Mode6UnitCode.x25,1,"",0),
	x26(Mode6UnitCode.x26,1,"",0),
	x27(Mode6UnitCode.x17,0.01,"",0),
	x28(Mode6UnitCode.x28,1,"",0),
	x29(Mode6UnitCode.x29,0.25,"",0),
	x2A(Mode6UnitCode.x2A,0.001,"",0),
	x2B(Mode6UnitCode.x2B,1,"",0),
	x2C(Mode6UnitCode.x2C,0.01,"",0),
	x2D(Mode6UnitCode.x2D,0.01,"",0),
	x2E(Mode6UnitCode.x2E,1,"",0),
	x2F(Mode6UnitCode.x2F,0.01,"",0),

	x30(Mode6UnitCode.x30,0.0015,"",0),
	x31(Mode6UnitCode.x31,0.001,"",0),
	x32(Mode6UnitCode.x32,0.0000305,"",0),
	x33(Mode6UnitCode.x33,0.000244,"",0),
	x34(Mode6UnitCode.x34,1,"min",0),
	x35(Mode6UnitCode.x35,0.01,"s",0),
	x36(Mode6UnitCode.x36,0.01,"",0),
	x37(Mode6UnitCode.x37,0.1,"",0),
	x38(Mode6UnitCode.x38,1,"",0),
	x39(Mode6UnitCode.x39,0.01,"%",-327),


	x81(Mode6UnitCode.x81,1,"",-32768),
	x82(Mode6UnitCode.x82,0.1,"",-3276.8),
	x83(Mode6UnitCode.x83,0.01,"",-327.68),
	x84(Mode6UnitCode.x84,0.001,"",-32.768),
	x85(Mode6UnitCode.x85,0.0000305,"",-0.999),
	x86(Mode6UnitCode.x86,0.000305,"",-9.994),
	x8A(Mode6UnitCode.x8A,0.000122,"v",-3.99);







	private String code;
	private double per;
	private String unit;
	private double minValue;
	Mode6Enum(String code, double per, String unit, double minValue){
		this.code = code;
		this.per = per;
		this.unit = unit;
		this.minValue = minValue;
	}

	public static Mode6Enum getBean(String code){
		Mode6Enum data=null;
		for(Mode6Enum item : Mode6Enum.values()){
			if(item.getCode().equals(code)){
				data=item;
				break;
			}
		}
		return data;
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public double getPer() {
		return per;
	}

	public void setPer(double per) {
		this.per = per;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

}
