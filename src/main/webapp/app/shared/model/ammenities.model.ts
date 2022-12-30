import { IReview } from 'app/shared/model/review.model';
import { AmmenitiesEnum } from 'app/shared/model/enumerations/ammenities-enum.model';

export interface IAmmenities {
  id?: number;
  ammenity?: AmmenitiesEnum | null;
  review?: IReview | null;
}

export const defaultValue: Readonly<IAmmenities> = {};
