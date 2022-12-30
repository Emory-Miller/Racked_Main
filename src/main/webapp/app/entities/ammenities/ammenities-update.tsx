import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IReview } from 'app/shared/model/review.model';
import { getEntities as getReviews } from 'app/entities/review/review.reducer';
import { IAmmenities } from 'app/shared/model/ammenities.model';
import { AmmenitiesEnum } from 'app/shared/model/enumerations/ammenities-enum.model';
import { getEntity, updateEntity, createEntity, reset } from './ammenities.reducer';

export const AmmenitiesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const reviews = useAppSelector(state => state.review.entities);
  const ammenitiesEntity = useAppSelector(state => state.ammenities.entity);
  const loading = useAppSelector(state => state.ammenities.loading);
  const updating = useAppSelector(state => state.ammenities.updating);
  const updateSuccess = useAppSelector(state => state.ammenities.updateSuccess);
  const ammenitiesEnumValues = Object.keys(AmmenitiesEnum);

  const handleClose = () => {
    navigate('/ammenities');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getReviews({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...ammenitiesEntity,
      ...values,
      review: reviews.find(it => it.id.toString() === values.review.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ammenity: 'BATHROOM',
          ...ammenitiesEntity,
          review: ammenitiesEntity?.review?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rackedApp.ammenities.home.createOrEditLabel" data-cy="AmmenitiesCreateUpdateHeading">
            <Translate contentKey="rackedApp.ammenities.home.createOrEditLabel">Create or edit a Ammenities</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="ammenities-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rackedApp.ammenities.ammenity')}
                id="ammenities-ammenity"
                name="ammenity"
                data-cy="ammenity"
                type="select"
              >
                {ammenitiesEnumValues.map(ammenitiesEnum => (
                  <option value={ammenitiesEnum} key={ammenitiesEnum}>
                    {translate('rackedApp.AmmenitiesEnum.' + ammenitiesEnum)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="ammenities-review"
                name="review"
                data-cy="review"
                label={translate('rackedApp.ammenities.review')}
                type="select"
              >
                <option value="" key="0" />
                {reviews
                  ? reviews.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ammenities" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AmmenitiesUpdate;
