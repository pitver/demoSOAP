package ru.vershinin.demoSOAP.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;
@ApiModel(description = "Class output of the operation result.")
public class Result implements Serializable {

    @ApiModelProperty(notes = "operation result",
            example = "4", required = true, position = 0)
    int res;

    public Result() {
    }

    public Result(int res) {
        this.res = res;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return res == result.res;
    }

    @Override
    public int hashCode() {
        return Objects.hash(res);
    }

    @Override
    public String toString() {
        return "Result{" +
                "res=" + res +
                '}';
    }
}
