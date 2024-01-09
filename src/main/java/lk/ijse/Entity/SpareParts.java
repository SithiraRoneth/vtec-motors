/* Created By Sithira Roneth
 * Date :12/23/23
 * Time :00:44
 * Project Name :vtec-motors
 * */
package lk.ijse.Entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SpareParts {
    private String spareId;
    private String spareType;
    private String description;
    private double price;
    private String service_name;
    private String service_id;
}
