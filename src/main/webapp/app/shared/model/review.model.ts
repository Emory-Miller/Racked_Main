import { IUser } from 'app/shared/model/user.model';
import { IPostRack } from 'app/shared/model/post-rack.model';
import { IAmmenities } from 'app/shared/model/ammenities.model';
import { StarRating } from 'app/shared/model/enumerations/star-rating.model';
import { RackSize } from 'app/shared/model/enumerations/rack-size.model';

export interface IReview {
  id?: number;
  starRating?: StarRating | null;
  rackSize?: RackSize | null;
  comments?: string | null;
  user?: IUser | null;
  postRack?: IPostRack | null;
  ammenities?: IAmmenities[] | null;
}

export const defaultValue: Readonly<IReview> = {};
