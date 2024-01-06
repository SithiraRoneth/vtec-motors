/* Created By Sithira Roneth
 * Date :1/3/24
 * Time :00:01
 * Project Name :vtec-motors
 * */
package lk.ijse.Entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Salary {
    private String id;
    private String name;
    private double salary;
    private double bonus;
    private double etf;
    private double finalSalary;
    private String month;
}
