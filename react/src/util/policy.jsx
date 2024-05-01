import { TYPE_EMPLOYER, TYPE_GS, TYPE_STUDENT } from '../constants';

export function canCreateOffer(user) {
	return user.type === TYPE_EMPLOYER || user.type === TYPE_GS;
}

export function canSeeAllOffers(user) {
	return user.type === TYPE_GS;
}

export function canEditOffer(user) {
	return user.type === TYPE_EMPLOYER || user.type === TYPE_GS
}

export function canRestrictOffer(user) {
	return user.type === TYPE_GS;
}

export function canSeeStudentOffers(user) {
	return user.type === TYPE_STUDENT;
}

export function canCreateOrganization(user) {
	return user.type === TYPE_EMPLOYER;
}

export function canOwnOffers(user) {
	return user.type === TYPE_EMPLOYER;
}

export function canSeeGestionFormulaireEmployerStudent(user){
	return user.type === TYPE_EMPLOYER || user.type === TYPE_STUDENT;
}

export function canSeeGestionFormulaireInternshipManager(user){
	return user.type === TYPE_GS;
}

export function canCreateFormulaireInternshipManager(user){
	return user.type === TYPE_GS;
}

export function canSendNotification(user){
	return user.type === TYPE_GS;
}

export function canSeeListOfCV(user){
	return user.type === TYPE_GS;
}

export function canSeeListOfApplications(user) {
	return user.type === TYPE_GS;
}

export function canApply(user) {
	return user.type === TYPE_STUDENT;
}

export function canSeeApplicationsOnOffer(user) {
	return user.type === TYPE_EMPLOYER;
}

export function canUploadCv(user) {
	return user.type === TYPE_STUDENT;
}